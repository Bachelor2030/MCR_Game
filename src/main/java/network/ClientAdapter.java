package network;

import network.jsonUtils.InitParser;
import network.states.ClientSharedState;
import network.states.ClientThreadState;
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

    private final static int BUFFER_SIZE = 1024;
    private String host;
    private int port;

    private Socket clientSocket = null;
    private BufferedReader inBufferedReader = null;
    private PrintWriter outPrintWriter = null;

    private ClientThreadState clientThreadState = ClientThreadState.SERVER_LISTENING;
    private ClientSharedState clientSharedState;


    boolean finishedInit = false;

    public ClientAdapter(String host, int port, String playerName) {
        this.host = host;
        this.port = port;
        this.clientSharedState = new ClientSharedState();
        this.clientSharedState.setPlayerName(playerName);
    }

    public ClientSharedState getClientSharedState() {
        return clientSharedState;
    }

    public void run() {
        // Setting up connection
        setup(host, port);

        try {
            // Greeting the server
            greetings(clientSharedState.getPlayerName());

            while (clientThreadState != ClientThreadState.GAME_ENDED && clientThreadState != ClientThreadState.ERROR) {
                switch (clientThreadState) {
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
            clientThreadState = ClientThreadState.ERROR;
            exit();
        }
        clientThreadState = ClientThreadState.CLIENT_LISTENING;
    }

    private void awaitServerMessage() {
        String receivedAnswer;

        try {
            while (clientThreadState == ClientThreadState.CLIENT_LISTENING
                    && (receivedAnswer = receiveJson(inBufferedReader.readLine())) != null) {
                String jsonType = readJsonType(receivedAnswer);

                switch (jsonType) {
                    case Messages.JSON_TYPE_GAME_START:
                        clientThreadState = ClientThreadState.CLIENT_LISTENING;
                        break;

                    case Messages.JSON_TYPE_INIT:
                        clientThreadState = getStateFromTurnInit(receivedAnswer);
                        InitParser initParser = new InitParser();

                        clientSharedState.setEnemyName(initParser.readPlayerName(receivedAnswer));
                        // todo: Parse the rest of init and determine how to give it to the gui (if not same way as for enemy name)
                        clientSharedState.setFinishedInit(true);
                        break;

                    case Messages.JSON_TYPE_YOUR_TURN:
                        clientThreadState = ClientThreadState.SERVER_LISTENING;
                        break;

                    case Messages.JSON_TYPE_WAIT_TURN:
                        clientThreadState = ClientThreadState.CLIENT_LISTENING;
                        break;

                    case Messages.JSON_TYPE_GAME_END:
                        clientThreadState = ClientThreadState.GAME_ENDED;
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
            clientThreadState = ClientThreadState.CLIENT_LISTENING;
            printMessage(clientClassName(), "Played a valid move. Waiting for server game update");

        } else if (jsonType.equals(Messages.JSON_TYPE_PLAY_BAD)) {
            clientThreadState = ClientThreadState.SERVER_LISTENING;
            printMessage(clientClassName(), "You donkey, you played the wrong card! Play again.");
        } else {
            clientThreadState = ClientThreadState.ERROR;
        }
    }



    public void exit() {
        if (clientThreadState == ClientThreadState.ERROR) {
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