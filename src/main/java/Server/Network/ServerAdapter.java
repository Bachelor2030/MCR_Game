package Server.Network;

import Common.Network.Messages;
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

    int port;

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

            while (true) {
                printMessage("Receptionist", "Waiting (blocking) for a new client on port " + port);
                try {
                    Socket clientSocket = serverSocket.accept();
                    printMessage("Receptionist", "A new client has arrived. Starting a new thread and delegating work to a new servant...");
                    new Thread(new ServantWorker(clientSocket)).start();
                } catch (IOException ex) {
                    printMessage(MessageLevel.Error, "Receptionist", ex.toString());
                }
            }
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

            boolean running = false;

            public ServantWorker(Socket clientSocket) {
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
                    awaitClientIO(false, "Reading until client sends greetings to open the connection...");

                    // Awaiting client goodbye
                    awaitClientIO(true, "Reading until client sends goodbye or closes the connection...");

                    cleanupResources();

                } catch (IOException | JSONException ex) {
                    runErrorCleanup(ex);
                }
            }

            private void awaitClientIO(boolean shouldRun, String consoleMessage) throws IOException, JSONException {
                String receivedMessage;
                printMessage("Servant", consoleMessage);

                while (running == shouldRun
                        && (receivedMessage = readJsonMessage(inBufferedReader.readLine())) != null) {
                    switch(receivedMessage) {
                        case Messages.CLIENT_HELLO:
                            running = true;
                            sendJsonMessage(Messages.SERVER_HELLO_ANS);
                            break;

                        case Messages.CLIENT_GOODBYE:
                            running = false;
                            sendJsonMessage(Messages.SERVER_GOODBYE_ANS);
                            break;

                        default:
                            sendJsonMessage(Messages.SERVER_UNKNOWN_ANS);
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

            private void sendJson(JSONObject json) {
                outPrintWriter.println(json.toString());
                outPrintWriter.flush();
            }

            private void sendJsonMessage(String message) throws JSONException {
                printMessage("Servant", "-> " + message);

                JSONObject json = new JSONObject();
                json.put("message", message);

                sendJson(json);
            }

            private String readJsonMessage(String jsonMessage) {
                String message;

                try {
                    JSONObject obj = new JSONObject(jsonMessage);
                    message = obj.getString("message");
                    printMessage("Servant", "<- " + message);

                    return message;

                } catch (JSONException e) {
                    debugMessage("Answer was not Json!");

                    return "Error";
                }
            }

            /*************************************************************************************************
             *                                        END OF UTILITIES                                       *
             ************************************************************************************************/
        }
    }
}