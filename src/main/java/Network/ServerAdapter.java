package Network;

import GameLogic.Game;
import GameLogic.Invocator.Card.Card;
import GameLogic.Receptors.Player;
import Network.JsonUtils.CardJsonParser;
import Network.JsonUtils.JsonUtil;
import Network.States.ServerState;
import Network.States.WorkerState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static Network.JsonUtils.ParserLauncher.parseJsonCards;
import static Network.Utilities.Info.*;
import static Network.Utilities.JsonServer.sendJson;
import static Network.Utilities.JsonServer.sendJsonType;
import static Network.Utilities.NetworkWaiting.awaitClientHandshake;
import static Network.Utilities.NetworkWaiting.awaitClientMessage;
import static Network.Utilities.Streams.cleanupResources;
import static Network.States.WorkerState.INIT;


public class ServerAdapter {

    private final String jsonPath = "src/main/resources/json/";

    private int port;
    ArrayList<Card> allCards;
    Game game;
    ServerState serverState;

    /**
     * Constructor
     *
     * @param port the port to listen on
     */
    public ServerAdapter(int port, int nbrLines, int lineLength) {
        this.port = port;
        this.game = new Game(this, nbrLines, lineLength);
        serverState = new ServerState(game.getFirstPlayerId());

        try {
            allCards = parseJsonCards(new JsonUtil().getJsonContent(jsonPath + "cards.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method initiates the process. The server creates a socket and binds it
     * to the previously specified port. It then waits for clients in a infinite
     * loop. When a client arrives, the server will read its input line by line
     * and send back the data converted to uppercase. This will continue until the
     * client sends the "BYE" command.
     */
    public void serveClients() {
        printMessage("ServerAdapter", "Starting the Receptionist Worker on a new thread...");
        new Thread(new ReceptionistWorker()).start();
    }

    /**
     * This inner class implements the behavior of the "receptionist", whose
     * responsibility is to listen for incoming connection requests. As soon as a
     * new client has arrived, the receptionist delegates the processing to a
     * "servant" who will execute on its own thread.
     */
    private class ReceptionistWorker implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket;

            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                printMessage(MessageLevel.Error, receptionistClassName(), ex.toString());
                return;
            }

            while (serverState.getPlayerCount() < 2) {
                printMessage(MessageLevel.Info, receptionistClassName(), "Waiting (blocking) for a new player on port " + port);

                try {
                    Socket clientSocket = serverSocket.accept();
                    serverState.incrementPlayerCount();
                    printMessage(MessageLevel.Info, receptionistClassName(), "A new player has arrived. Starting a new thread and delegating work to a new servant...");
                    new Thread(new ServantWorker(clientSocket, serverState.getPlayerCount())).start();
                } catch (IOException ex) {
                    printMessage(MessageLevel.Error, receptionistClassName(), ex.toString());
                }
            }
            printMessage(receptionistClassName(), "Two players have connected to the server. Stop listening for new connexions.");


            // Wait for player names to have been set
            while (!serverState.playerNamesSet()) {}

            CardJsonParser cardJsonParser = new CardJsonParser();
            List<Card> deck1 = cardJsonParser.parseJson(jsonPath + "cards1.json", allCards);
            List<Card> deck2 = cardJsonParser.parseJson(jsonPath + "cards2.json", allCards);

            game.initGame(new Player(serverState.getPlayerName(1), deck1), new Player(serverState.getPlayerName(2), deck2));
        }

        /**
         * This inner class implements the behavior of the "servants", whose
         * responsibility is to take care of clients once they have connected. This
         * is where we implement the application protocol logic, i.e. where we read
         * data sent by the client and where we generate the responses.
         */
        private class ServantWorker implements Runnable {
            Socket clientSocket;
            BufferedReader inBufferedReader = null;
            PrintWriter outPrintWriter = null;

            int playerId;

            public ServantWorker(Socket clientSocket, int playerId) {
                this.playerId = playerId;

                printMessage(servantClassName(playerId), "Starting worker for player " + playerId);
                try {
                    this.clientSocket = clientSocket;
                    inBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    outPrintWriter = new PrintWriter(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    printMessage(MessageLevel.Error, servantClassName(playerId), ex.toString());
                }
            }

            @Override
            public void run() {
                try {
                    // Awaiting client greetings
                    awaitClientHandshake(serverState, inBufferedReader, outPrintWriter, playerId, servantClassName(playerId));

                    // Awaiting client messages
                    while (serverState.getWorkerState(playerId) != WorkerState.GAME_ENDED) {
                        switch (serverState.getWorkerState(playerId)) {
                            case INIT:
                                // Wait for game to finish init, parsing, etc...
                                while (!serverState.finishedInit()) {}

                                if (playerId == serverState.getPlayingId()) {
                                    serverState.setWorkerState(playerId, WorkerState.SERVER_LISTENING);
                                } else {
                                    serverState.setWorkerState(playerId, WorkerState.CLIENT_LISTENING);
                                }

                                JSONObject init;
                                if (playerId == game.getFirstPlayerId()) {
                                    init = game.initStateP1();
                                } else {
                                    init = game.initStateP2();
                                }
                                sendJson(init, outPrintWriter, servantClassName(playerId));
                                break;

                            case INIT_WAITING:
                                while (serverState.getPlayerCount() < 2 || serverState.getPlayerName(serverState.otherPlayer(playerId)) == null) {}

                                sendJsonType(Messages.JSON_TYPE_GAME_START, outPrintWriter, servantClassName(playerId));
                                serverState.setWorkerState(playerId, INIT);
                                break;

                            case SERVER_LISTENING:
                                awaitClientMessage(game, serverState, inBufferedReader, outPrintWriter, playerId, servantClassName(playerId));
                                break;

                            case CLIENT_LISTENING:
                                while (serverState.getIntendToSendJson(playerId)) {
                                    while(!serverState.jsonToSendEmpty(playerId)) {
                                        JSONObject jsonObject = serverState.popJsonToSend(playerId);
                                        sendJson(jsonObject, outPrintWriter, servantClassName(playerId));
                                    }
                                }

                                // Wait for backend to signify it's next player's turn
                                while (serverState.getPlayingId() != playerId) {}

                                // TODO: Send "your turn" from logic class?
                                sendJsonType(Messages.JSON_TYPE_YOUR_TURN, outPrintWriter, servantClassName(playerId));
                                serverState.setWorkerState(playerId, WorkerState.SERVER_LISTENING);
                                break;

                            case GAME_ENDED:
                                sendJsonType(Messages.JSON_TYPE_GAME_END, outPrintWriter, servantClassName(playerId));
                                break;
                        }
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                } finally {
                    serverState.decrementPlayerCount();
                    cleanupResources(servantClassName(playerId), inBufferedReader, outPrintWriter, clientSocket);
                }
            }
        }
    }
}