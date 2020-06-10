package Client.Network;

import Common.Network.ClientState;
import Common.Network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientAdapter {

    final static int BUFFER_SIZE = 1024;
    String host;
    int port;

    Socket clientSocket = null;
    BufferedReader inBufferedReader = null;
    PrintWriter outPrintWriter = null;

    ClientState state = ClientState.SERVER_LISTENING;

    public ClientAdapter(String host, int port, String playerName) {
        // Setting up connection
        setup(host, port);

        try {
            // Greeting the server
            greetings(playerName);

            while (state != ClientState.GAME_ENDED && state != ClientState.ERROR) {
                switch (state) {
                    case CLIENT_LISTENING:
                        System.out.println("Client listening");
                        awaitServerMessage();

                        break;
                    case SERVER_LISTENING:
                        System.out.println("Server listening");
                        awaitClientInput();
                        break;
                }
            }

            // Saying goodbye to the server
            exit();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void awaitServerMessage() {
        String receivedAnswer;

        try {
            while (state == ClientState.CLIENT_LISTENING
                    && (receivedAnswer = receiveJson(inBufferedReader.readLine())) != null) {
                String jsonType = readJsonType(receivedAnswer);

                switch (jsonType) {
                    case Messages.JSON_TYPE_GAME_START:
                        state = ClientState.CLIENT_LISTENING;
                        break;

                    case Messages.JSON_TYPE_INIT:
                        state = getStateFromTurnInit(receivedAnswer);
                        break;

                    case Messages.JSON_TYPE_YOUR_TURN:
                        state = ClientState.SERVER_LISTENING;
                        break;

                    case Messages.JSON_TYPE_WAIT_TURN:
                        state = ClientState.CLIENT_LISTENING;
                        break;

                    case Messages.JSON_TYPE_GAME_END:
                        state = ClientState.GAME_ENDED;
                        break;

                    default:
                        sendJsonType(Messages.JSON_TYPE_UNKNOWN);
                        break;
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void awaitClientInput() throws IOException, JSONException {
        Scanner in = new Scanner(System.in);
        System.out.println("Waiting for client input");
        while (in.nextLine( ).length( ) > 0) {}

        String jsonAnswer = sendJsonType(Messages.JSON_TYPE_PLAY);
        String jsonType = readJsonType(jsonAnswer);

        if (jsonType.equals(Messages.JSON_TYPE_PLAY_OK)) {
            state = ClientState.CLIENT_LISTENING;
            printMessage("Played a valid move. Waiting for server game update");

        } else if (jsonType.equals(Messages.JSON_TYPE_PLAY_BAD)) {
            state = ClientState.SERVER_LISTENING;
            printMessage("You donkey, you played the wrong card!");
        } else {
            state = ClientState.ERROR;
        }
    }

    public void greetings(String playerName) throws IOException, JSONException {

        JSONObject obj = new JSONObject();
        obj.put(Messages.JSON_TYPE, Messages.JSON_TYPE_HELLO);
        obj.put(Messages.JSON_TYPE_PLAYERNAME, playerName);


        String jsonType = readJsonType(sendJson(obj));

        if (jsonType.equals(Messages.JSON_TYPE_WAIT_PLAYER)) {
            printMessage("Successful connection to the server. Waiting for Player 2");
        } else if (jsonType.equals(Messages.JSON_TYPE_GAME_START)) {
            printMessage("Successful connection to the server. Player1 is already connected. Starting the game.");
        } else {
            printMessage("Unexpected server answer. Exiting the game");
            state = ClientState.ERROR;
            exit();
        }
        state = ClientState.CLIENT_LISTENING;
    }

    public void exit() throws IOException, JSONException {
        if (state == ClientState.ERROR) {
            printMessage("An error occured. Trying to exit properly");
        } else {
            printMessage("Exiting the game");
        }
        String answer = sendJsonType(Messages.JSON_TYPE_GOODBYE);
        String type = readJsonType(answer);
        cleanupResources();

        if (type.equals(Messages.JSON_TYPE_GOODBYE_ANS)) {
            printMessage("Exited as expected");
            System.exit(0);
        } else {
            printMessage("Unexpected server answer: '" + answer + "'. Expected '" + Messages.JSON_TYPE_GOODBYE_ANS + "'");
            System.exit(1);
        }
    }

    public void setup(String host, int port) {
        this.host = host;
        this.port = port;

        try {
            clientSocket = new Socket(host, port);
            inBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outPrintWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (UnknownHostException e) {
            printError("Unknown host error", e);
        } catch (IOException e) {
            printError("IO Error", e);
        }
    }

    public void cleanupResources() {
        try {
            inBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPrintWriter.close();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***********************************************************************************************************
     *                                           UTILITIES                                                     *
     **********************************************************************************************************/

    private String sendJson(JSONObject jsonObject) throws IOException, JSONException {
        printMessage("-> " + jsonObject.toString());
        outPrintWriter.println(jsonObject.toString());
        outPrintWriter.flush();

        return receiveJson(inBufferedReader.readLine());
    }

    private String receiveJson(String jsonMessage) throws JSONException {
        printMessage("<- " + jsonMessage);

        JSONObject obj = new JSONObject(jsonMessage);
        return obj.toString();

    }

    private JSONObject jsonType(String message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Messages.JSON_TYPE, message);

        return json;
    }

    private String sendJsonType(String type) throws JSONException, IOException {
        return sendJson(jsonType(type));
    }

    private String readJsonType(String jsonMessage) throws JSONException {
        JSONObject obj = new JSONObject(jsonMessage);
        return obj.getString(Messages.JSON_TYPE);
    }

    private ClientState getStateFromTurnInit(String jsonMessage) throws JSONException {
        JSONObject obj = new JSONObject(jsonMessage);

        JSONObject gameState = obj.getJSONObject(Messages.JSON_GAMESTATE);

        String turn = gameState.getString(Messages.JSON_TYPE_TURN);

        if (turn.equals(Messages.JSON_TYPE_YOUR_TURN)) {
            printMessage("It's your turn");
            return ClientState.SERVER_LISTENING;
        } else if (turn.equals(Messages.JSON_TYPE_WAIT_TURN)) {
            printMessage("Wait for your turn");
            return ClientState.CLIENT_LISTENING;
        } else {
            return ClientState.ERROR;
        }
    }

    private void printError(String message, Exception e) {
        System.out.println(message);
        System.out.println(e);
    }

    private void printMessage(String msg) {
        System.out.println(msg);
    }

    /***********************************************************************************************************
     *                                        END OF UTILITIES                                                 *
     **********************************************************************************************************/
}