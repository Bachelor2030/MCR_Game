package Network.Utilities;

import GameLogic.Game;
import Network.Messages;
import Network.States.ServerState;
import Network.States.WorkerState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static Network.Utilities.Info.*;
import static Network.Utilities.JsonServer.*;
import static Network.Utilities.JsonServer.sendJsonType;

public class NetworkWaiting {

    public static void awaitClientHandshake(ServerState serverState, BufferedReader inBufferedReader, PrintWriter outPrintWriter, int playerId, String className) throws IOException, JSONException {
        String receivedMessage;
        printMessage(className, "Reading until client sends greetings to open the connection...");

        while (serverState.getWorkerState(playerId) == WorkerState.CONNECTING
                && (receivedMessage = readJson(inBufferedReader.readLine(), className)) != null) {

            String type = readJsonType(receivedMessage, className);
            if (type.equals(Messages.JSON_TYPE_HELLO)) {
                String playerName = readJsonPlayerName(receivedMessage, className);
                serverState.setPlayerName(playerId, playerName);

                if (serverState.getPlayerCount() == 1) {
                    sendJsonType(Messages.JSON_TYPE_WAIT_PLAYER, outPrintWriter, className);
                    serverState.setWorkerState(playerId, WorkerState.INIT_WAITING);
                } else if (serverState.getPlayerCount() == 2) {
                    sendJsonType(Messages.JSON_TYPE_GAME_START, outPrintWriter, className);
                    serverState.setWorkerState(playerId, WorkerState.INIT);
                } else {
                    debugMessage("Weird. You have " + serverState.getPlayerCount() + " players. This should not have happened");
                    serverState.setWorkerState(playerId, WorkerState.CONNECTING);
                }
            } else {
                sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter, className);
                serverState.setWorkerState(playerId, WorkerState.CONNECTING);
            }
        }
    }

    public static void awaitClientMessage(Game game, ServerState serverState, BufferedReader inBufferedReader, PrintWriter outPrintWriter, int playerId, String className) throws IOException, JSONException {
        String receivedMessage;
        printMessage(className, "Reading until client sends messages or closes the connection...");

        while (serverState.getWorkerState(playerId) == WorkerState.SERVER_LISTENING
                && (receivedMessage = readJson(inBufferedReader.readLine(), className)) != null) {

            String type = readJsonType(receivedMessage, className);
            switch (type) {
                case Messages.JSON_TYPE_PLAY:
                    boolean goodPlay;
                    goodPlay = game.playerSentMessage(playerId, receivedMessage);

                    // wait for new play
                    if (goodPlay) {
                        serverState.setIntendToSendJson(playerId, true);
                        sendJsonType(Messages.JSON_TYPE_PLAY_OK, outPrintWriter, className);
                        // Sending all game updates
                        if (serverState.getIntendToSendJson(playerId)) {
                            while(!serverState.jsonToSendEmpty(playerId)) {
                                JSONObject jsonObject = serverState.popJsonToSend(playerId);
                                sendJson(jsonObject, outPrintWriter, servantClassName(playerId));
                            }
                        }
                        serverState.setIntendToSendJson(playerId, false);
                    } else {
                        sendJsonType(Messages.JSON_TYPE_PLAY_BAD, outPrintWriter, className);
                    }
                    break;

                case Messages.JSON_TYPE_GOODBYE:
                    serverState.setWorkerState(playerId, WorkerState.GAME_ENDED);
                    sendJsonType(Messages.JSON_TYPE_GOODBYE_ANS, outPrintWriter, className);
                    return;

                default:
                    sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter, className);
                    return;
            }
        }
    }
}
