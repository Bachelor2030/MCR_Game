package Common.Network.Utilities;

import Common.Network.Messages;
import Common.Network.WorkerState;
import Server.Network.ServerAdapter;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static Common.Network.Utilities.Info.*;
import static Common.Network.Utilities.JsonServer.*;
import static Common.Network.Utilities.JsonServer.sendJsonType;

public class NetworkWaiting {

    public static void awaitClientHandshake(ServerAdapter serverAdapter, ServerState serverState, BufferedReader inBufferedReader, PrintWriter outPrintWriter, String className) throws IOException, JSONException {
        String receivedMessage;
        printMessage(className, "Reading until client sends greetings to open the connection...");

        while (serverState.getWorkerState() == WorkerState.CONNECTING
                && (receivedMessage = readJson(inBufferedReader.readLine(), className)) != null) {

            String type = readJsonType(receivedMessage, className);
            if (type.equals(Messages.JSON_TYPE_HELLO)) {

                if (serverAdapter.getPlayerCount() == 1) {
                    sendJsonType(Messages.JSON_TYPE_WAIT_PLAYER, outPrintWriter, className);
                    serverState.setWorkerState(WorkerState.INIT_WAITING);
                } else if (serverAdapter.getPlayerCount() == 2) {
                    sendJsonType(Messages.JSON_TYPE_GAME_START, outPrintWriter, className);
                    serverState.setWorkerState(WorkerState.INIT);
                } else {
                    debugMessage("Weird. You have " + serverAdapter.getPlayerCount() + " players. This should not have happened");
                    serverState.setWorkerState(WorkerState.CONNECTING);
                }
            } else {
                sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter, className);
                serverState.setWorkerState(WorkerState.CONNECTING);
            }
        }
    }

    public static void awaitClientMessage(ServerAdapter serverAdapter, ServerState serverState, BufferedReader inBufferedReader, PrintWriter outPrintWriter, String className) throws IOException, JSONException {
        String receivedMessage;
        printMessage(className, "Reading until client sends messages or closes the connection...");

        while (serverState.getWorkerState() != WorkerState.GAME_ENDED
                && (receivedMessage = readJson(inBufferedReader.readLine(), className)) != null) {

            String type = readJsonType(receivedMessage, className);
            switch (type) {
                case Messages.JSON_TYPE_PLAY:

                    if (serverAdapter.getPlayingId() == 1) {
                        sendJsonType(Messages.JSON_TYPE_PLAY_OK, outPrintWriter, className);
                        serverState.setWorkerState(WorkerState.CLIENT_LISTENING);
                        serverAdapter.nextPlayer();
                        return;
                    } else {
                        sendJsonType(Messages.JSON_TYPE_PLAY_BAD, outPrintWriter, className);
                        break; // wait for new play
                    }

                case Messages.JSON_TYPE_GOODBYE:
                    serverState.setWorkerState(WorkerState.GAME_ENDED);
                    sendJsonType(Messages.JSON_TYPE_GOODBYE_ANS, outPrintWriter, className);
                    return;

                default:
                    sendJsonType(Messages.JSON_TYPE_UNKNOWN, outPrintWriter, className);
                    return;
            }
        }
    }

}
