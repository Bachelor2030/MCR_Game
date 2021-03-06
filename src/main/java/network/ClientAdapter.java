package network;

import gui.GameBoard;
import javafx.application.Platform;
import network.jsonUtils.GUIParser;
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

import static network.utilities.Info.clientClassName;
import static network.utilities.Info.printMessage;
import static network.utilities.JsonClient.*;

public class ClientAdapter {

  private static final int BUFFER_SIZE = 1024;
  private String host;
  private int port;

  private Socket clientSocket = null;
  private BufferedReader inBufferedReader = null;
  private PrintWriter outPrintWriter = null;

  private ClientThreadState clientThreadState = ClientThreadState.SERVER_LISTENING;
  private ClientSharedState clientSharedState;

  private GameBoard gameBoard;

  boolean finishedInit = false;

  /**
   * Constructeur de ClientAdapter
   * @param gameBoard La gameBoard utilisée par le GUI
   * @param host L'ip du serveur
   * @param port Le port utilisé par le serveur
   * @param playerName Le nom du joueur
   */
  public ClientAdapter(GameBoard gameBoard, String host, int port, String playerName) {
    this.host = host;
    this.port = port;
    this.clientSharedState = new ClientSharedState();
    this.clientSharedState.setPlayerName(playerName);
    this.gameBoard = gameBoard;
  }

  public ClientSharedState getClientSharedState() {
    return clientSharedState;
  }

  /**
   * Fonction principale du ClientAdapter.
   * Fonction bloquante tant que l'état du client n'est pas "GAME_ENDED"
   * Alterne entre attendre un input client et un message serveur
   */
  public void run() {
    // Setting up connection
    setup(host, port);

    try {
      // Greeting the server
      greetings(clientSharedState.getPlayerName(), clientSharedState.getEnemyImagePath());

      while (clientThreadState != ClientThreadState.GAME_ENDED
          && clientThreadState != ClientThreadState.ERROR) {
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

  /**
   * Bind le socket à un host et un port
   * @param host L'adresse IP du serveur
   * @param port Le port sélectionné
   */
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

  /**
   * Envoie les greetings (handshake) au serveur
   * @param playerName Le nom du joueur envoyant ses greetings au serveur
   * @param imagePath Le path de l'image qu'il a sélectionée
   * @throws IOException Si une erreur d'IO survient (dans les streams)
   * @throws JSONException Si le message reçu ou envoyé n'est pas conforme au json
   */
  public void greetings(String playerName, String imagePath) throws IOException, JSONException {

    JSONObject obj = new JSONObject();
    obj.put(Messages.JSON_TYPE, Messages.JSON_TYPE_HELLO);
    obj.put(Messages.JSON_TYPE_PLAYERNAME, playerName);
    obj.put(Messages.JSON_TYPE_IMAGE, imagePath);

    String jsonType = readJsonType(sendJson(obj, outPrintWriter, inBufferedReader));

    if (jsonType.equals(Messages.JSON_TYPE_WAIT_PLAYER)) {
      printMessage(clientClassName(), "Successful connection to the server. Waiting for Player 2");
    } else if (jsonType.equals(Messages.JSON_TYPE_GAME_START)) {
      printMessage(
          clientClassName(),
          "Successful connection to the server. Player1 is already connected. Starting the game.");
    } else {
      printMessage(
          Info.MessageLevel.Error, clientClassName(), "Unexpected server answer. Exiting the game");
      clientThreadState = ClientThreadState.ERROR;
      exit();
    }
    clientThreadState = ClientThreadState.CLIENT_LISTENING;
  }

  /**
   * Attente active sur un message provenant du serveur.
   * Le message sera traité en fonction de son champ "type"
   */
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
            gameBoard.sendInit(receivedAnswer);
            GUIParser initParser = new GUIParser(receivedAnswer, getClientSharedState());

            clientSharedState.setEnemyName(initParser.getEnemyFromInit()[0]);
            clientSharedState.setEnemyImagePath(initParser.getEnemyFromInit()[1]);
            clientSharedState.setFinishedInit(true);
            break;

          case Messages.JSON_TYPE_UPDATE:
            final String update = receivedAnswer;
            Platform.runLater(
                () -> {
                  GUIParser.getCommand(update, gameBoard.getGuiBoard()).execute(gameBoard);
                  gameBoard.updateStage();
                });
            break;

          case Messages.JSON_TYPE_UPDATE_END:
            clientThreadState = ClientThreadState.SERVER_LISTENING;

          case Messages.JSON_TYPE_YOUR_TURN:
            clientThreadState = ClientThreadState.SERVER_LISTENING;
            clientSharedState.setMyTurn(true);
            break;

          case Messages.JSON_TYPE_WAIT_TURN:
            clientThreadState = ClientThreadState.CLIENT_LISTENING;
            clientSharedState.setMyTurn(false);
            break;

          case Messages.JSON_TYPE_GAME_END:
            clientThreadState = ClientThreadState.GAME_ENDED;
            clientSharedState.setMyTurn(false);
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

  /**
   * Attente active sur le json à envoyer par le client (généré depuis la GUI)
   * Joue le coup donné, et vérifie la réponse du serveur concernant la
   * validité du coup.
   * @throws IOException Lors d'une erreur d'IO sur les streams
   * @throws JSONException Si le json à envoyer n'est pas conforme
   */
  private void awaitClientInput() throws IOException, JSONException {
    System.out.println("Waiting for client input");

    while (!clientSharedState.getIntendToSendJson()) {}

    if (clientSharedState.jsonToSendEmpty()) {
      return;
    }

    JSONObject play = clientSharedState.popJsonToSend();

    clientSharedState.setIntendToSendJson(false);

    String jsonAnswer = sendJson(play, outPrintWriter, inBufferedReader);
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

  /**
   * Engage la procédure pour couper la connexion au serveur et quitter le jeu
   */
  public void exit() {
    if (clientThreadState == ClientThreadState.ERROR) {
      printMessage(
          Info.MessageLevel.Error, clientClassName(), "An error occured. Trying to exit properly");
    } else {
      printMessage(clientClassName(), "Exiting the game");
    }

    String answer = "Answer not received";
    String type = "Type not received"; // By default, we don't know if we will get an answer

    try {
      answer = sendJsonType(Messages.JSON_TYPE_GOODBYE, outPrintWriter, inBufferedReader);
      type = readJsonType(answer);
    } catch (IOException | JSONException e) {
      printMessage(
          Info.MessageLevel.Error,
          clientClassName(),
          "Unexpected server answer: '"
              + answer
              + "'. Expected '"
              + Messages.JSON_TYPE_GOODBYE_ANS
              + "'");
      e.printStackTrace();
      System.exit(1);
    }
    cleanupResources();

    if (type.equals(Messages.JSON_TYPE_GOODBYE_ANS)) {
      printMessage(clientClassName(), "Exited as expected");
      System.exit(0);
    }
  }

  /**
   * Ferme les différents streams et objets ouverts par le client
   */
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
