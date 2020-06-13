package Network.Utilities;

import Network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;

import static Network.Utilities.Info.printMessage;

public abstract class JsonServer {

    public static void sendJson(JSONObject jsonObject, PrintWriter outPrintWriter, String className) {
        printMessage(className, "-> " + jsonObject.toString());
        outPrintWriter.println(jsonObject.toString());
        outPrintWriter.flush();
    }

    public static void sendJsonType(String message, PrintWriter outPrintWriter, String className) throws JSONException {
        JSONObject json = new JSONObject();
        json.put(Messages.JSON_TYPE, message);

        sendJson(json, outPrintWriter, className);
    }

    public static String readJson(String jsonMessage, String className) {
        printMessage(className, "<- " + jsonMessage);
        try {
            JSONObject obj = new JSONObject(jsonMessage);
            return obj.toString();
        } catch (JSONException e) {
            return jsonError(className);
        }
    }

    public static String readJsonType(String jsonMessage, String className) {
        String type;

        try {
            JSONObject obj = new JSONObject(jsonMessage);
            type = obj.getString(Messages.JSON_TYPE);

            return type;

        } catch (JSONException e) {
            return jsonError(className);
        }
    }

    public static String readJsonPlayerName(String jsonMessage, String className) {
        String name;

        try {
            JSONObject obj = new JSONObject(jsonMessage);
            name = obj.getString(Messages.JSON_TYPE_PLAYERNAME);

            return name;

        } catch (JSONException e) {
            return jsonError(className);
        }
    }

    public static String jsonError(String className) {
        printMessage(className, "Answer was not Json!");

        return "Error";
    }
}
