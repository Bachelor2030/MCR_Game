package network;

import network.states.ClientState;
import network.utilities.Info;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import static network.utilities.Info.clientClassName;
import static network.utilities.Info.printMessage;
import static network.utilities.JsonClient.*;

public class ClientAdapter {

    final static int BUFFER_SIZE = 1024;
    String host;
    int port;

    Socket clientSocket = null;
    BufferedReader inBufferedReader = null;
    PrintWriter outPrintWriter = null;

    ClientState state = ClientState.SERVER_LISTENING;
    String playerName;

    public ClientAdapter(String host, int port, String playerName) {
        this.host = host;
        this.port = port;
        this.playerName = playerName;
    }

    public void run() {
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
            System.exit(1);
        }
    }

    public void setup(String host, int port) {
        try {
            clientSocket = new Socket(host, port);
            inBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outPrintWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (UnknownHostException e) {
            printMessage(Info.MessageLevel.Error, clientClassName(), "Unknown Host Exception");
            e.printStackTrace();
        } catch (IOException e) {
            printMessage(Info.MessageLevel.Error, clientClassName(), "IO Exception");
            e.printStackTrace();
        }
    }

    public void greetings(String playerName) throws IOException, JSONException {

        JSONObject obj = new JSONObject();
        obj.put(Messages.JSON_TYPE, Messages.JSON_TYPE_HELLO);
        obj.put(Messages.JSON_TYPE_PLAYERNAME, playerName);


        String jsonType = readJsonType(sendJson(obj, outPrintWriter, inBufferedReader));

        if (jsonType.equals(Messages.JSON_TYPE_WAIT_PLAYER)) {
            printMessage(clientClassName(), "Successful connection to the server. Waiting for Player 2");
        } else if (jsonType.equals(Messages.JSON_TYPE_GAME_START)) {
            printMessage(clientClassName(), "Successful connection to the server. Player1 is already connected. Starting the game.");
        } else {
            printMessage(Info.MessageLevel.Error, clientClassName(), "Unexpected server answer. Exiting the game");
            state = ClientState.ERROR;
            exit();
        }
        state = ClientState.CLIENT_LISTENING;
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
                        sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter, inBufferedReader);
                        break;
                }
            }
        } catch (SocketException e) {
            printMessage(Info.MessageLevel.Error, clientClassName(), "Connection to the server reset.");
            e.printStackTrace();
            System.exit(1);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void awaitClientInput() throws IOException, JSONException {
        Scanner in = new Scanner(System.in);
        System.out.println("Waiting for client input");
        while (in.nextLine( ).length( ) > 0) {}

        String jsonAnswer = sendJsonType(Messages.JSON_TYPE_PLAY, outPrintWriter, inBufferedReader);
        String jsonType = readJsonType(jsonAnswer);

        if (jsonType.equals(Messages.JSON_TYPE_PLAY_OK)) {
            state = ClientState.CLIENT_LISTENING;
            printMessage(clientClassName(), "Played a valid move. Waiting for server game update");

        } else if (jsonType.equals(Messages.JSON_TYPE_PLAY_BAD)) {
            state = ClientState.SERVER_LISTENING;
            printMessage(clientClassName(), "You donkey, you played the wrong card! Play again.");
        } else {
            state = ClientState.ERROR;
        }
    }



    public void exit() {
        if (state == ClientState.ERROR) {
            printMessage(Info.MessageLevel.Error, clientClassName(), "An error occured. Trying to exit properly");
        } else {
            printMessage(clientClassName(), "Exiting the game");
        }

        String answer = "Answer not received";
        String type = "Type not received"; // By default, we don't know if we will get an answer

        try {
            answer = sendJsonType(Messages.JSON_TYPE_GOODBYE, outPrintWriter, inBufferedReader);
            type = readJsonType(answer);
        } catch (IOException | JSONException e) {
            printMessage(Info.MessageLevel.Error, clientClassName(), "Unexpected server answer: '" + answer + "'. Expected '" + Messages.JSON_TYPE_GOODBYE_ANS + "'");
            e.printStackTrace();
            System.exit(1);
        }
        cleanupResources();

        if (type.equals(Messages.JSON_TYPE_GOODBYE_ANS)) {
            printMessage(clientClassName(), "Exited as expected");
            System.exit(0);
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
}