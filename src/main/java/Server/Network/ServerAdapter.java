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


public class ServerAdapter {

    private enum MessageLevel {
        Info, Error
    }

    private void printMessage(MessageLevel level, String className, String message) {
        System.out.println(level + ": " + className + ": " + message);
    }

    private void printMessage(String className, String message) {
        printMessage(MessageLevel.Info, className, message);
    }

    private void debugMessage(String message) {
        printMessage("Debug", message);
    }

    private int port;
    private int players = 0;

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
     *
     * @param port the port to listen on
     */
    public ServerAdapter(int port) {
        this.port = port;
    }

    /**
     * This method initiates the process. The server creates a socket and binds it
     * to the previously specified port. It then waits for clients in a infinite
     * loop. When a client arrives, the server will read its input line by line
     * and send back the data converted to uppercase. This will continue until the
     * client sends the "BYE" command.
     */
    public void serveClients() {
        printMessage("Main", "Starting the Receptionist Worker on a new thread...");
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
                printMessage(MessageLevel.Error, "Receptionist", ex.toString());
                return;
            }

            while (incrementPlayers() <= 2) {
                printMessage("Receptionist", "Waiting (blocking) for a new player on port " + port);
                try {
                    Socket clientSocket = serverSocket.accept();
                    printMessage("Receptionist", "A new player has arrived. Starting a new thread and delegating work to a new servant...");
                    new Thread(new ServantWorker(clientSocket, getPlayers())).start();
                } catch (IOException ex) {
                    printMessage(MessageLevel.Error, "Receptionist", ex.toString());
                }
            }
            printMessage("Receptionist", "Two players have connected to the server. Stop listening for new connexions.");
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

            public ServantWorker(Socket clientSocket, int playerId) {
                this.playerId = playerId;
                printMessage("ServantWorker", "Starting worker for player " + playerId);
                try {
                    this.clientSocket = clientSocket;
                    inBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    outPrintWriter = new PrintWriter(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    printMessage(MessageLevel.Error, "Servant", ex.toString());
                }
            }

            @Override
            public void run() {
                try {
                    // Awaiting client greetings
                    awaitClientHandshake();

                    // Awaiting client messages
                    while (state != ServerState.GAME_ENDED) {

                        switch(state) {
                            case INIT:
                                JSONObject json = new JSONObject();

                                json.put(Messages.JSON_GAMESTATE, "Test");
                                json.put(Messages.JSON_TYPE, Messages.JSON_TYPE_INIT);
                                sendJson(json);
                                state = ServerState.SERVER_LISTENING;
                                break;

                            case INIT_WAITING:
                                while (getPlayers() < 2) {}
                                sendJsonType(Messages.JSON_TYPE_GAME_START);
                                state = ServerState.INIT;
                                break;

                            case SERVER_LISTENING:
                                awaitClientMessage();
                                break;

                            case CLIENT_LISTENING:

                                break;

                            case GAME_ENDED:
                                sendJsonType(Messages.JSON_TYPE_GAME_END);
                                break;

                            default:
                                break;
                        }
                    }
                    cleanupResources();
                } catch (IOException | JSONException ex) {
                    runErrorCleanup(ex);
                } finally {
                    decrementPlayers();
                }
            }

            private void awaitClientHandshake() throws IOException, JSONException {
                String receivedMessage;
                printMessage("Servant", "Reading until client sends greetings to open the connection...");

                while (state == ServerState.CONNECTING
                        && (receivedMessage = readJson(inBufferedReader.readLine())) != null) {

                    String type = readJsonType(receivedMessage);
                    if (type.equals(Messages.JSON_TYPE_HELLO)) {

                        if (getPlayers() == 1) {
                            sendJsonType(Messages.JSON_TYPE_WAIT_PLAYER);
                            state = ServerState.INIT_WAITING;
                        } else if (getPlayers() == 2) {
                            sendJsonType(Messages.JSON_TYPE_GAME_START);
                            state = ServerState.INIT;
                        } else {
                            debugMessage("WTF DUDE YOU HAVE " + getPlayers() + " PLAYERS");
                            state = ServerState.CONNECTING;
                        }
                    } else {
                        sendJsonType(Messages.JSON_TYPE_UNKNOWN);
                        state = ServerState.CONNECTING;
                    }
                }
            }

            private void awaitClientMessage() throws IOException, JSONException {
                String receivedMessage;
                printMessage("Servant", "Reading until client sends messages or closes the connection...");

                while (state != ServerState.GAME_ENDED
                        && (receivedMessage = readJson(inBufferedReader.readLine())) != null) {

                    String type = readJsonType(receivedMessage);
                    switch (type) {
                        case Messages.JSON_TYPE_GOODBYE:
                            state = ServerState.GAME_ENDED;
                            sendJsonType(Messages.JSON_TYPE_GOODBYE_ANS);
                            break;

                        default:
                            sendJsonType(Messages.JSON_TYPE_UNKNOWN);
                            break;
                    }
                }
            }

            private void runErrorCleanup(Exception ex) {
                if (inBufferedReader != null) {
                    try {
                        inBufferedReader.close();
                    } catch (IOException ex1) {
                        printMessage(MessageLevel.Error, "Servant", ex1.getMessage());
                    }
                }
                if (outPrintWriter != null) {
                    outPrintWriter.close();
                }
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException ex1) {
                        printMessage(MessageLevel.Error, "Servant", ex1.getMessage());
                    }
                }
                printMessage(MessageLevel.Error, "Servant", ex.getMessage());
            }

            private void cleanupResources() throws IOException {
                printMessage("Servant", "Cleaning up resources...");

                clientSocket.close();
                inBufferedReader.close();
                outPrintWriter.close();
            }

            /*************************************************************************************************
             *                                           UTILITIES                                           *
             ************************************************************************************************/

            private void sendJson(JSONObject jsonObject) {
                printMessage("Servant", "-> " + jsonObject.toString());
                outPrintWriter.println(jsonObject.toString());
                outPrintWriter.flush();
            }

            private void sendJsonType(String message) throws JSONException {
                JSONObject json = new JSONObject();
                json.put(Messages.JSON_TYPE, message);

                sendJson(json);
            }

            private String readJson(String jsonMessage) {
                printMessage("Servant", "<- " + jsonMessage);
                try {
                    JSONObject obj = new JSONObject(jsonMessage);
                    return obj.toString();
                } catch (JSONException e) {
                    return jsonError();
                }
            }

            private String readJsonType(String jsonMessage) {
                String type;

                try {
                    JSONObject obj = new JSONObject(jsonMessage);
                    type = obj.getString(Messages.JSON_TYPE);

                    return type;

                } catch (JSONException e) {
                    return jsonError();
                }
            }

            private String readJsonPlayerName(String jsonMessage) {
                String name;

                try {
                    JSONObject obj = new JSONObject(jsonMessage);
                    name = obj.getString(Messages.JSON_TYPE_PLAYERNAME);

                    return name;

                } catch (JSONException e) {
                    return jsonError();
                }
            }

            private String jsonError() {
                debugMessage("Answer was not Json!");

                return "Error";
            }

            /*************************************************************************************************
             *                                        END OF UTILITIES                                       *
             ************************************************************************************************/
        }
    }
}