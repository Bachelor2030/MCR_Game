package network;

import gameLogic.Game;
import gameLogic.commands.Create;
import gameLogic.invocator.card.Card;
import gameLogic.invocator.card.CardType;
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

import static network.jsonUtils.JsonUtil.parseJsonCards;
import static network.states.ServerThreadState.CLIENT_LISTENING;
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
   * Constructeur de ServerAdapter
   * @param port Le port sur lequel écouter
   * @param nbrLines Le nombre de lignes composant le jeu [Non utilisé]
   * @param lineLength La longueur des lignes [Non utilisé]
   */
  public ServerAdapter(int port, int nbrLines, int lineLength) {
    this.port = port;
    this.game = new Game(this, nbrLines, lineLength);
    serverSharedState = new ServerSharedState(game.getFirstPlayerId(), game);

    try {
      allCards = parseJsonCards(new JsonUtil().getJsonContent(jsonPath + "cards.json"), serverSharedState);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method initiates the process. The server creates a socket and binds it to the previously
   * specified port. It then waits for clients in a infinite loop.
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

      // On attend d'avoir deux joueurs
      while (serverSharedState.getPlayerCount() < 2) {
        printMessage(
            MessageLevel.Info,
            receptionistClassName(),
            "Waiting (blocking) for a new Player on port " + port);

        try {
          clientSocket = serverSocket.accept();
          serverSharedState.incrementPlayerCount();
          printMessage( MessageLevel.Info, receptionistClassName(),
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

      // On parse les cartes des joueurs
      CardJsonParser cardJsonParser = new CardJsonParser();
      List<Card> deck1 = cardJsonParser.parseJson(jsonPath + "cardsnew.json", allCards);
      for (Card card : deck1) {
          if (card.getType() == CardType.CREATURE) {
            for (Create create : card.getCommand().getCreateCreature()) {
                create.getCreature().setOwner(game.getPlayer(game.getFirstPlayerId()));
            }
          }
      }
      List<Card> deck2 = cardJsonParser.parseJson(jsonPath + "cardsnew.json", allCards);
      for (Card card : deck2) {
        if (card.getType() == CardType.CREATURE) {
          for (Create create : card.getCommand().getCreateCreature()) {
            create.getCreature().setOwner(game.getPlayer((game.getFirstPlayerId() == 1 ? 2 : 1)));
          }
        }
      }

      game.initGame(
          new Player(serverSharedState.getPlayerName(1), deck1, game, serverSharedState),
          new Player(serverSharedState.getPlayerName(2), deck2, game, serverSharedState));
    }

    /**
     * This inner class implements the behavior of the "servants", whose responsibility is to take
     * care of clients once they have connected.
     */
    private class ServantWorker implements Runnable {
      Socket clientSocket;
      BufferedReader inBufferedReader = null;
      PrintWriter outPrintWriter = null;

      int playerId;

      /**
       * Constructeur de ServantWorker
       * @param clientSocket Le socket sur lequel le client est connecté
       * @param playerId L'id du joueur connecté
       */
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

                // Wait for other player to connect
              case INIT_WAITING:
                while (serverSharedState.getPlayerCount() < 2
                    || serverSharedState.getPlayerName(serverSharedState.otherPlayer(playerId))
                        == null) {}

                sendJsonType(
                    Messages.JSON_TYPE_GAME_START, outPrintWriter, servantClassName(playerId));
                serverSharedState.setWorkerState(playerId, INIT);
                break;

                // Wait for client message
              case SERVER_LISTENING:
                awaitClientMessage(
                    game,
                    serverSharedState,
                    inBufferedReader,
                    outPrintWriter,
                    playerId,
                    servantClassName(playerId));
                break;

                // Client is waiting for update
              case CLIENT_LISTENING:
                // Wait for backend to signify it's next Player's turn
                while ( serverSharedState.getPlayingId() != playerId &&
                        serverSharedState.getWorkerState(playerId) == CLIENT_LISTENING) {}

                if (serverSharedState.getIntendToSendJson(playerId)) {
                  while (!serverSharedState.jsonToSendEmpty(playerId)) {
                    JSONObject jsonObject = serverSharedState.popJsonToSend(playerId);
                    sendJson(jsonObject, outPrintWriter, servantClassName(playerId));
                  }
                }

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
}
