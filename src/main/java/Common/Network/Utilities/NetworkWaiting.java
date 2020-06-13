package Common.Network.Utilities;

import Common.Network.Messages;
import Common.Network.States.PlayState;
import Common.Network.States.ServerState;
import Common.Network.States.WorkerState;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static Common.Network.Utilities.Info.*;
import static Common.Network.Utilities.JsonServer.*;
import static Common.Network.Utilities.JsonServer.sendJsonType;

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

    public static void awaitClientMessage(ServerState serverState, BufferedReader inBufferedReader, PrintWriter outPrintWriter, int playerId, String className) throws IOException, JSONException {
        String receivedMessage;
        printMessage(className, "Reading until client sends messages or closes the connection...");

        while (serverState.getWorkerState(playerId) == WorkerState.SERVER_LISTENING
                && (receivedMessage = readJson(inBufferedReader.readLine(), className)) != null) {

            String type = readJsonType(receivedMessage, className);
            switch (type) {
                case Messages.JSON_TYPE_PLAY:
                    serverState.setPlayStates(playerId, PlayState.EVAL_PLAY);

                    // TODO: Remove this (here only for testing purposes, failing at random)
                    if (Math.random() < 0.5) {
                        serverState.setPlayStates(playerId, PlayState.PLAY_OK);
                    } else {
                        serverState.setPlayStates(playerId, PlayState.PLAY_BAD);
                    }

                    // Wait for the game logic to decide if the play was good or bad
                    while (serverState.getPlayStates(playerId) == PlayState.EVAL_PLAY) {}

                    switch (serverState.getPlayStates(playerId)) {
                        case PLAY_OK:
                            serverState.setWorkerState(playerId, WorkerState.CLIENT_LISTENING);
                            serverState.setPlayStates(playerId, PlayState.WAIT_TURN);
                            sendJsonType(Messages.JSON_TYPE_PLAY_OK, outPrintWriter, className);
                            serverState.nextPlayer();
                            return;

                        case PLAY_BAD:
                            serverState.setPlayStates(playerId, PlayState.WAIT_PLAY);
                            sendJsonType(Messages.JSON_TYPE_PLAY_BAD, outPrintWriter, className);
                            break; // wait for new play
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
