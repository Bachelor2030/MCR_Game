package Server.Network;

import Common.Network.Messages;
import Common.Network.ServerState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static Common.Network.Utils.*;


public class ServerAdapter {

    private int port;
    private int playing;
    private int players = 0;

    public synchronized int getPlaying() {
        return playing;
    }

    public synchronized void nextPlayer() {
        this.playing = ((playing++) % 2) + 1;
    }

    public synchronized int getPlayers() {
        return players;
    }

    public synchronized int incrementPlayers() {
        return ++players;
    }

    public synchronized int decrementPlayers() {
        return --players;
    }


    /**
     * Constructor
     * @param port the port to listen on
     */
    public ServerAdapter(int port, int firstPlayerId) {
        this.port = port;
        playing = firstPlayerId;
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
                printMessage(MessageLevel.Error, receptionistName, ex.toString());
                return;
            }

            while (getPlayers() < 2) {
                printMessage(receptionistName, "Waiting (blocking) for a new player on port " + port);
                try {
                    Socket clientSocket = serverSocket.accept();
                    incrementPlayers();
                    printMessage(receptionistName, "A new player has arrived. Starting a new thread and delegating work to a new servant...");
                    new Thread(new ServantWorker(clientSocket, getPlayers())).start();
                } catch (IOException ex) {
                    printMessage(MessageLevel.Error, receptionistName, ex.toString());
                }
            }
            printMessage(receptionistName, "Two players have connected to the server. Stop listening for new connexions.");
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

            ServerState state = ServerState.CONNECTING;
            int playerId;

            private String servantNameForPlayer(int playerId) {
                return servantWorkerName + playerId;
            }

            public ServantWorker(Socket clientSocket, int playerId) {
                this.playerId = playerId;
                printMessage(servantWorkerName, "Starting worker for player " + playerId);
                try {
                    this.clientSocket = clientSocket;
                    inBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    outPrintWriter = new PrintWriter(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    printMessage(MessageLevel.Error, servantWorkerName, ex.toString());
                }
            }

            @Override
            public void run() {
                try {
                    // Awaiting client greetings
                    awaitClientHandshake();

                    // Awaiting client messages
                    while (state != ServerState.GAME_ENDED) {

                        switch (state) {
                            case INIT:
                                JSONObject turn;
                                if (playerId == getPlaying()) {
                                    turn = new JSONObject().put(Messages.JSON_TYPE_TURN, Messages.JSON_TYPE_YOUR_TURN);
                                    state = ServerState.SERVER_LISTENING;
                                } else {
                                    turn = new JSONObject().put(Messages.JSON_TYPE_TURN, Messages.JSON_TYPE_WAIT_TURN);
                                    state = ServerState.CLIENT_LISTENING;
                                }

                                JSONObject gamestate = new JSONObject();

                                gamestate.put(Messages.JSON_TYPE, Messages.JSON_TYPE_INIT);
                                gamestate.put(Messages.JSON_GAMESTATE, turn);

                                sendJson(gamestate, outPrintWriter);
                                break;

                            case INIT_WAITING:
                                while (getPlayers() < 2) {
                                }
                                sendJsonType(Messages.JSON_TYPE_GAME_START, outPrintWriter);
                                state = ServerState.INIT;
                                break;

                            case SERVER_LISTENING:
                                awaitClientMessage();
                                break;

                            case CLIENT_LISTENING:
                                while (getPlaying() != playerId) {
                                }
                                sendJsonType(Messages.JSON_TYPE_YOUR_TURN, outPrintWriter);
                                state = ServerState.SERVER_LISTENING;
                                break;

                            case GAME_ENDED:
                                sendJsonType(Messages.JSON_TYPE_GAME_END, outPrintWriter);
                                break;

                            default:
                                break;
                        }
                    }
                    cleanupResources(inBufferedReader, outPrintWriter, clientSocket);
                } catch (IOException | JSONException ex) {
                    cleanUpResourcesError(ex, inBufferedReader, outPrintWriter, clientSocket);
                } finally {
                    decrementPlayers();
                }
            }

            private void awaitClientHandshake() throws IOException, JSONException {
                String receivedMessage;
                printMessage(servantNameForPlayer(playerId), "Reading until client sends greetings to open the connection...");

                while (state == ServerState.CONNECTING
                        && (receivedMessage = readJson(inBufferedReader.readLine())) != null) {

                    String type = readJsonType(receivedMessage);
                    if (type.equals(Messages.JSON_TYPE_HELLO)) {

                        if (getPlayers() == 1) {
                            sendJsonType(Messages.JSON_TYPE_WAIT_PLAYER, outPrintWriter);
                            state = ServerState.INIT_WAITING;
                        } else if (getPlayers() == 2) {
                            sendJsonType(Messages.JSON_TYPE_GAME_START, outPrintWriter);
                            state = ServerState.INIT;
                        } else {
                            debugMessage("WTF DUDE YOU HAVE " + getPlayers() + " PLAYERS");
                            state = ServerState.CONNECTING;
                        }
                    } else {
                        sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter);
                        state = ServerState.CONNECTING;
                    }
                }
            }

            private void awaitClientMessage() throws IOException, JSONException {
                String receivedMessage;
                printMessage(servantNameForPlayer(playerId), "Reading until client sends messages or closes the connection...");

                while (state != ServerState.GAME_ENDED
                        && (receivedMessage = readJson(inBufferedReader.readLine())) != null) {

                    String type = readJsonType(receivedMessage);
                    switch (type) {
                        case Messages.JSON_TYPE_PLAY:
                            state = ServerState.CLIENT_LISTENING;
                            nextPlayer();
                            sendJsonType(Messages.JSON_TYPE_PLAY_OK, outPrintWriter);
                            break;

                        case Messages.JSON_TYPE_GOODBYE:
                            state = ServerState.GAME_ENDED;
                            sendJsonType(Messages.JSON_TYPE_GOODBYE_ANS, outPrintWriter);
                            break;

                        default:
                            sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter);
                            break;
                    }
                }
            }
        }
    }
}