package network;

import gameLogic.Game;
import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
import network.jsonUtils.CardJsonParser;
import network.jsonUtils.JsonUtil;
import network.states.ServerSharedState;
import network.states.ServerThreadState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static network.jsonUtils.ParserLauncher.parseJsonCards;
import static network.states.ServerThreadState.INIT;
import static network.utilities.Info.*;
import static network.utilities.JsonServer.sendJson;
import static network.utilities.JsonServer.sendJsonType;
import static network.utilities.NetworkWaiting.awaitClientHandshake;
import static network.utilities.NetworkWaiting.awaitClientMessage;
import static network.utilities.Streams.cleanupResources;

public class ServerAdapter {

  private final String jsonPath = "src/main/resources/json/";

  private int port;
  private Socket clientSocket;
  ArrayList<Card> allCards;
  Game game;
  ServerSharedState serverSharedState;

  /**
   * Constructor
   *
   * @param port the port to listen on
   */
  public ServerAdapter(int port, int nbrLines, int lineLength) {
    this.port = port;
    this.game = new Game(this, nbrLines, lineLength);
    serverSharedState = new ServerSharedState(game.getFirstPlayerId(), game);

    try {
      allCards = parseJsonCards(new JsonUtil().getJsonContent(jsonPath + "cards.json"));
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method initiates the process. The server creates a socket and binds it to the previously
   * specified port. It then waits for clients in a infinite loop. When a client arrives, the server
   * will read its input line by line and send back the data converted to uppercase. This will
   * continue until the client sends the "BYE" command.
   */
  public void serveClients() {
    printMessage("ServerAdapter", "Starting the Receptionist Worker on a new thread...");
    new Thread(new ReceptionistWorker()).start();
  }

  public ServerSharedState getServerSharedState() {
    return serverSharedState;
  }

  /**
   * This inner class implements the behavior of the "receptionist", whose responsibility is to
   * listen for incoming connection requests. As soon as a new client has arrived, the receptionist
   * delegates the processing to a "servant" who will execute on its own thread.
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

      while (serverSharedState.getPlayerCount() < 2) {
        printMessage(
            MessageLevel.Info,
            receptionistClassName(),
            "Waiting (blocking) for a new Player on port " + port);

        try {
          clientSocket = serverSocket.accept();
          serverSharedState.incrementPlayerCount();
          printMessage(
              MessageLevel.Info,
              receptionistClassName(),
              "A new Player has arrived. Starting a new thread and delegating work to a new servant...");
          new Thread(new ServantWorker(clientSocket, serverSharedState.getPlayerCount())).start();
        } catch (IOException ex) {
          printMessage(MessageLevel.Error, receptionistClassName(), ex.toString());
        }
      }
      printMessage(
          receptionistClassName(),
          "Two players have connected to the server. Stop listening for new connexions.");

      // Wait for Player names to have been set
      while (!serverSharedState.playerNamesSet()) {}

      CardJsonParser cardJsonParser = new CardJsonParser();
      List<Card> deck1 = cardJsonParser.parseJson(jsonPath + "cards1.json", allCards);
      List<Card> deck2 = cardJsonParser.parseJson(jsonPath + "cards2.json", allCards);

      game.initGame(
          new Player(serverSharedState.getPlayerName(1), deck1, game),
          new Player(serverSharedState.getPlayerName(2), deck2, game));
    }

    /**
     * This inner class implements the behavior of the "servants", whose responsibility is to take
     * care of clients once they have connected. This is where we implement the application protocol
     * logic, i.e. where we read data sent by the client and where we generate the responses.
     */
    private class ServantWorker implements Runnable {
      Socket clientSocket;
      BufferedReader inBufferedReader = null;
      PrintWriter outPrintWriter = null;

      int playerId;

      public ServantWorker(Socket clientSocket, int playerId) {
        this.playerId = playerId;

        printMessage(servantClassName(playerId), "Starting worker for Player " + playerId);
        try {
          this.clientSocket = clientSocket;
          inBufferedReader =
              new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          outPrintWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException ex) {
          printMessage(MessageLevel.Error, servantClassName(playerId), ex.toString());
        }
      }

      @Override
      public void run() {
        try {
          // Awaiting client greetings
          awaitClientHandshake(
              serverSharedState,
              inBufferedReader,
              outPrintWriter,
              playerId,
              servantClassName(playerId));

          // Awaiting client messages
          while (!serverSharedState.gameEnded()) {
            switch (serverSharedState.getWorkerState(playerId)) {
              case INIT:
                // Wait for game to finish init, parsing, etc...
                while (!serverSharedState.finishedInit()) {}
                if (playerId == serverSharedState.getPlayingId()) {
                  serverSharedState.setWorkerState(playerId, ServerThreadState.SERVER_LISTENING);
                } else {
                  serverSharedState.setWorkerState(playerId, ServerThreadState.CLIENT_LISTENING);
                }

                JSONObject init;
                init = game.initState(playerId);
                sendJson(init, outPrintWriter, servantClassName(playerId));
                break;

              case INIT_WAITING:
                while (serverSharedState.getPlayerCount() < 2
                    || serverSharedState.getPlayerName(serverSharedState.otherPlayer(playerId))
                        == null) {}

                sendJsonType(
                    Messages.JSON_TYPE_GAME_START, outPrintWriter, servantClassName(playerId));
                serverSharedState.setWorkerState(playerId, INIT);
                break;

              case SERVER_LISTENING:
                awaitClientMessage(
                    game,
                    serverSharedState,
                    inBufferedReader,
                    outPrintWriter,
                    playerId,
                    servantClassName(playerId));
                break;

              case CLIENT_LISTENING:
                while (serverSharedState.getIntendToSendJson(playerId)) {
                  while (!serverSharedState.jsonToSendEmpty(playerId)) {
                    JSONObject jsonObject = serverSharedState.popJsonToSend(playerId);
                    sendJson(jsonObject, outPrintWriter, servantClassName(playerId));
                  }
                }

                // Wait for backend to signify it's next Player's turn
                while (serverSharedState.getPlayingId() != playerId) {}

                // TODO: Send "your turn" from logic class?
                sendJsonType(
                    Messages.JSON_TYPE_YOUR_TURN, outPrintWriter, servantClassName(playerId));
                serverSharedState.setWorkerState(playerId, ServerThreadState.SERVER_LISTENING);
                break;
            }
          }
          if (serverSharedState.gameEnded()) {
            sendJsonType(Messages.JSON_TYPE_GAME_END, outPrintWriter, servantClassName(playerId));
          }
        } catch (JSONException | IOException e) {
          e.printStackTrace();
        } finally {
          serverSharedState.decrementPlayerCount();
          cleanupResources(
              servantClassName(playerId), inBufferedReader, outPrintWriter, clientSocket);
        }
      }
    }
  }

  public void closeClientSocket() {
    try {
      clientSocket.close();
    } catch (Exception e) {
      if (serverSharedState.gameEnded()) {
        printMessage(receptionistClassName(), "Closing server");
      } else {
        e.printStackTrace();
      }
    }
  }
}
