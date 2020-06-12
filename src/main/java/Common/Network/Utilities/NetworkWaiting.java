package Common.Network.Utilities;

import Common.Network.Messages;
import Common.Network.WorkerState;
import Server.Network.ServerAdapter;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static Common.Network.Utilities.Info.*;
import static Common.Network.Utilities.Json.*;
import static Common.Network.Utilities.Json.sendJsonType;

public class NetworkWaiting {
    public static void awaitClientMessage(ServerAdapter serverAdapter, WorkerState workerState, int playerId, BufferedReader inBufferedReader, PrintWriter outPrintWriter) throws IOException, JSONException {
        String receivedMessage;
        printMessage(servantNameForPlayer(playerId), "Reading until client sends messages or closes the connection...");

        while (workerState != WorkerState.GAME_ENDED
                && (receivedMessage = readJson(inBufferedReader.readLine())) != null) {

            String type = readJsonType(receivedMessage);
            switch (type) {
                case Messages.JSON_TYPE_PLAY:
                    workerState = WorkerState.CLIENT_LISTENING;
                    serverAdapter.nextPlayer();
                    sendJsonType(Messages.JSON_TYPE_PLAY_OK, outPrintWriter);
                    break;

                case Messages.JSON_TYPE_GOODBYE:
                    workerState = WorkerState.GAME_ENDED;
                    sendJsonType(Messages.JSON_TYPE_GOODBYE_ANS, outPrintWriter);
                    break;

                default:
                    sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter);
                    break;
            }
        }
    }

    public static void awaitClientHandshake(ServerAdapter serverAdapter, WorkerState workerState, int playerId, BufferedReader inBufferedReader, PrintWriter outPrintWriter) throws IOException, JSONException {
        String receivedMessage;
        printMessage(servantNameForPlayer(playerId), "Reading until client sends greetings to open the connection...");

        while (workerState == WorkerState.CONNECTING
                && (receivedMessage = readJson(inBufferedReader.readLine())) != null) {

            String type = readJsonType(receivedMessage);
            if (type.equals(Messages.JSON_TYPE_HELLO)) {

                if (serverAdapter.getPlayerCount() == 1) {
                    sendJsonType(Messages.JSON_TYPE_WAIT_PLAYER, outPrintWriter);
                    workerState = WorkerState.INIT_WAITING;
                } else if (serverAdapter.getPlayerCount() == 2) {
                    sendJsonType(Messages.JSON_TYPE_GAME_START, outPrintWriter);
                    workerState = WorkerState.INIT;
                } else {
                    debugMessage("Weird. You have " + serverAdapter.getPlayerCount() + " players. This should not have happened");
                    workerState = WorkerState.CONNECTING;
                }
            } else {
                sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter);
                workerState = WorkerState.CONNECTING;
            }
        }
    }
}
