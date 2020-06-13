package Server.Network;

import Common.Network.Messages;
import Common.Network.Utilities.ServerState;
import Common.Network.WorkerState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static Common.Network.Utilities.Info.*;
import static Common.Network.Utilities.JsonServer.sendJson;
import static Common.Network.Utilities.JsonServer.sendJsonType;
import static Common.Network.Utilities.NetworkWaiting.awaitClientHandshake;
import static Common.Network.Utilities.NetworkWaiting.awaitClientMessage;
import static Common.Network.Utilities.Streams.cleanupResources;
import static Common.Network.WorkerState.*;


public class ServerAdapter {

    private int port;
    private int playing;
    private int players = 0;

    public synchronized int getPlayingId() {
        return playing;
    }

    public synchronized void nextPlayer() {
        System.out.println("Playing before: " + this.playing);
        if (playing == 1) {
            playing = 2;
        } else {
            playing = 1;
        }
        System.out.println("Playing after: " + this.playing);
    }

    public synchronized int getPlayerCount() {
        return players;
    }

    public synchronized void incrementPlayerCount() {
        ++players;
    }

    public synchronized void decrementPlayerCount() {
        --players;
    }


    /**
     * Constructor
     *
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
                printMessage(MessageLevel.Error, receptionistClassName(), ex.toString());
                return;
            }

            while (getPlayerCount() < 2) {
                printMessage(MessageLevel.Info, receptionistClassName(), "Waiting (blocking) for a new player on port " + port);

                try {
                    Socket clientSocket = serverSocket.accept();
                    incrementPlayerCount();
                    printMessage(MessageLevel.Info, receptionistClassName(), "A new player has arrived. Starting a new thread and delegating work to a new servant...");
                    new Thread(new ServantWorker(clientSocket, getPlayerCount())).start();
                } catch (IOException ex) {
                    printMessage(MessageLevel.Error, receptionistClassName(), ex.toString());
                }
            }
            printMessage(receptionistClassName(), "Two players have connected to the server. Stop listening for new connexions.");
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

            ServerState serverState = new ServerState(WorkerState.CONNECTING);
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
                    awaitClientHandshake(ServerAdapter.this, serverState, inBufferedReader, outPrintWriter, servantClassName(playerId));

                    // Awaiting client messages
                    while (serverState.getWorkerState() != WorkerState.GAME_ENDED) {
                        switch (serverState.getWorkerState()) {
                            case INIT:
                                JSONObject turn;
                                if (playerId == getPlayingId()) {
                                    turn = new JSONObject().put(Messages.JSON_TYPE_TURN, Messages.JSON_TYPE_YOUR_TURN);
                                    serverState.setWorkerState(WorkerState.SERVER_LISTENING);
                                } else {
                                    turn = new JSONObject().put(Messages.JSON_TYPE_TURN, Messages.JSON_TYPE_WAIT_TURN);
                                    serverState.setWorkerState(WorkerState.CLIENT_LISTENING);
                                }

                                JSONObject gamestate = new JSONObject();

                                gamestate.put(Messages.JSON_TYPE, Messages.JSON_TYPE_INIT);
                                gamestate.put(Messages.JSON_GAMESTATE, turn);

                                sendJson(gamestate, outPrintWriter, servantClassName(playerId));
                                break;

                            case INIT_WAITING:
                                while (getPlayerCount() < 2) {
                                    printMessage(servantClassName(playerId), "Waiting for player 2");
                                }
                                sendJsonType(Messages.JSON_TYPE_GAME_START, outPrintWriter, servantClassName(playerId));
                                serverState.setWorkerState(INIT);
                                break;

                            case SERVER_LISTENING:
                                awaitClientMessage(ServerAdapter.this, serverState, inBufferedReader, outPrintWriter, servantClassName(playerId));
                                break;

                            case CLIENT_LISTENING:
                                while (getPlayingId() != playerId) {
                                }
                                sendJsonType(Messages.JSON_TYPE_YOUR_TURN, outPrintWriter, servantClassName(playerId));
                                serverState.setWorkerState(WorkerState.SERVER_LISTENING);
                                break;

                            case GAME_ENDED:
                                sendJsonType(Messages.JSON_TYPE_GAME_END, outPrintWriter, servantClassName(playerId));
                                break;
                        }
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                } finally {
                    decrementPlayerCount();
                    cleanupResources(servantClassName(playerId), inBufferedReader, outPrintWriter, clientSocket);
                }
            }
        }
    }
}