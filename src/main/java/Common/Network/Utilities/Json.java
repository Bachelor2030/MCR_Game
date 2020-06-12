package Common.Network.Utilities;

import Common.Network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;

public abstract class Json {

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
