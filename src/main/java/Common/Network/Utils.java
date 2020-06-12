package Common.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Utils {

    public static final String receptionistName = "Receptionist";
    public static final String servantWorkerName = "Servant";

    public enum MessageLevel {
        Info, Error
    }

    public static void printMessage(MessageLevel level, String className, String message) {
        System.out.println(level + ": " + className + ": " + message);
    }

    public static void printMessage(String className, String message) {
        printMessage(MessageLevel.Info, className, message);
    }

    public static void debugMessage(String message) {
        printMessage("Debug", message);
    }

    public static void cleanUpResourcesError(Exception ex, BufferedReader inBufferedReader, PrintWriter outPrintWriter, Socket clientSocket) {
        if (inBufferedReader != null) {
            try {
                inBufferedReader.close();
            } catch (IOException ex1) {
                //printMessage(ServerAdapter.MessageLevel.Error, servantName(), ex1.getMessage());
            }
        }
        if (outPrintWriter != null) {
            outPrintWriter.close();
        }
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException ex1) {
                //printMessage(ServerAdapter.MessageLevel.Error, servantName(), ex1.getMessage());
            }
        }
        //printMessage(ServerAdapter.MessageLevel.Error, servantName(), ex.getMessage());
    }

    public static void cleanupResources(BufferedReader inBufferedReader, PrintWriter outPrintWriter, Socket clientSocket) throws IOException {
        //printMessage(servantName(), "Cleaning up resources...");

        clientSocket.close();
        inBufferedReader.close();
        outPrintWriter.close();
    }

    public static void sendJson(JSONObject jsonObject, PrintWriter outPrintWriter) {
        //printMessage(servantName(), "-> " + jsonObject.toString());
        outPrintWriter.println(jsonObject.toString());
        outPrintWriter.flush();
    }

    public static void sendJsonType(String message, PrintWriter outPrintWriter) throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Messages.JSON_TYPE, message);

        sendJson(json, outPrintWriter);
    }

    public static String readJson(String jsonMessage) {
        //printMessage(servantName(), "<- " + jsonMessage);
        try {
            JSONObject obj = new JSONObject(jsonMessage);
            return obj.toString();
        } catch (JSONException e) {
            return jsonError();
        }
    }

    public static String readJsonType(String jsonMessage) {
        String type;

        try {
            JSONObject obj = new JSONObject(jsonMessage);
            type = obj.getString(Messages.JSON_TYPE);

            return type;

        } catch (JSONException e) {
            return jsonError();
        }
    }

    public static String readJsonPlayerName(String jsonMessage) {
        String name;

        try {
            JSONObject obj = new JSONObject(jsonMessage);
            name = obj.getString(Messages.JSON_TYPE_PLAYERNAME);

            return name;

        } catch (JSONException e) {
            return jsonError();
        }
    }

    public static String jsonError() {
        //debugMessage("Answer was not Json!");

        return "Error";
    }

}
