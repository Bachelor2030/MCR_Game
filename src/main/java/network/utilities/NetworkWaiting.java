package network.utilities;

import gameLogic.Game;
import network.Messages;
import network.states.ServerSharedState;
import network.states.ServerThreadState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static network.utilities.Info.*;
import static network.utilities.JsonServer.*;

public class NetworkWaiting {

  /**
   * Fonction bloquante qui attend qu'un client envoie une handshake après avoir bind une connexion
   * @param serverSharedState L'état partagé du serveur
   * @param inBufferedReader Le inBufferedReader dans lequel lire le message du client
   * @param outPrintWriter Le outPrintWriter dans lequel répondre au client
   * @param playerId L'id du joueur correspondant au thread appelant
   * @param className Le nom de la classe appelante
   * @throws IOException Si une erreur d'IO survient (dans les streams)
   * @throws JSONException Si le message reçu ou envoyé n'est pas conforme au json
   */
  public static void awaitClientHandshake(
      ServerSharedState serverSharedState,
      BufferedReader inBufferedReader,
      PrintWriter outPrintWriter,
      int playerId,
      String className)
      throws IOException, JSONException {
    String receivedMessage;
    printMessage(className, "Reading until client sends greetings to open the connection...");

    while (serverSharedState.getWorkerState(playerId) == ServerThreadState.CONNECTING
        && (receivedMessage = readJson(inBufferedReader.readLine(), className)) != null) {

      String type = readJsonType(receivedMessage, className);
      if (type.equals(Messages.JSON_TYPE_HELLO)) {
        String playerName = readJsonPlayerName(receivedMessage, className);
        serverSharedState.setPlayerName(playerId, playerName);

        if (serverSharedState.getPlayerCount() == 1) {
          sendJsonType(Messages.JSON_TYPE_WAIT_PLAYER, outPrintWriter, className);
          serverSharedState.setWorkerState(playerId, ServerThreadState.INIT_WAITING);
        } else if (serverSharedState.getPlayerCount() == 2) {
          sendJsonType(Messages.JSON_TYPE_GAME_START, outPrintWriter, className);
          serverSharedState.setWorkerState(playerId, ServerThreadState.INIT);
        } else {
          debugMessage(
              "Weird. You have "
                  + serverSharedState.getPlayerCount()
                  + " players. This should not have happened");
          serverSharedState.setWorkerState(playerId, ServerThreadState.CONNECTING);
        }
      } else {
        sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter, className);
        serverSharedState.setWorkerState(playerId, ServerThreadState.CONNECTING);
      }
    }
  }

  /**
   * @param game La classe représentant la logique serveur décidant de la validité des coups etc...
   * @param serverSharedState L'état partagé du serveur
   * @param inBufferedReader Le inBufferedReader dans lequel lire le message du client
   * @param outPrintWriter Le outPrintWriter dans lequel répondre au client
   * @param playerId L'id du joueur correspondant au thread appelant
   * @param className Le nom de la classe appelante
   * @throws IOException Si une erreur d'IO survient (dans les streams)
   * @throws JSONException Si le message reçu ou envoyé n'est pas conforme au json
   */
  public static void awaitClientMessage(
      Game game,
      ServerSharedState serverSharedState,
      BufferedReader inBufferedReader,
      PrintWriter outPrintWriter,
      int playerId,
      String className)
      throws IOException, JSONException {
    String receivedMessage;
    printMessage(className, "Reading until client sends messages or closes the connection...");

    while (serverSharedState.getWorkerState(playerId) == ServerThreadState.SERVER_LISTENING
        && (receivedMessage = readJson(inBufferedReader.readLine(), className)) != null) {

      String type = readJsonType(receivedMessage, className);
      switch (type) {
        case Messages.JSON_TYPE_UNDO:
        case Messages.JSON_TYPE_ABANDON:
        case Messages.JSON_TYPE_END_TURN:
        case Messages.JSON_TYPE_GAME_END:
        case Messages.JSON_TYPE_PLAY:
          boolean goodPlay;
          goodPlay = game.playerSentMessage(playerId, receivedMessage);

          // wait for new play
          if (goodPlay) {
            //serverSharedState.setIntendToSendJson(playerId, true);
            sendJsonType(Messages.JSON_TYPE_PLAY_OK, outPrintWriter, className);
            // Sending all game updates
            if (serverSharedState.getIntendToSendJson(playerId)) {
              while (!serverSharedState.jsonToSendEmpty(playerId)) {
                JSONObject jsonObject = serverSharedState.popJsonToSend(playerId);
                sendJson(jsonObject, outPrintWriter, servantClassName(playerId));
              }
            }
            serverSharedState.setIntendToSendJson(playerId, false);
          } else {
            sendJsonType(Messages.JSON_TYPE_PLAY_BAD, outPrintWriter, className);
          }
          break;

        case Messages.JSON_TYPE_GOODBYE:
          serverSharedState.endGame();
          sendJsonType(Messages.JSON_TYPE_GOODBYE_ANS, outPrintWriter, className);
          return;

        default:
          sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter, className);
          return;
      }
    }
  }
}
