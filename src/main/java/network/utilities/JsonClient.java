package network.utilities;

import network.Messages;
import network.states.ClientThreadState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static network.utilities.Info.clientClassName;
import static network.utilities.Info.printMessage;

public class JsonClient {

  public static String sendJson(
      JSONObject jsonObject, PrintWriter outPrintWriter, BufferedReader inBufferedReader)
      throws IOException, JSONException {
    printMessage(clientClassName(), "-> " + jsonObject.toString());
    outPrintWriter.println(jsonObject.toString());
    outPrintWriter.flush();

    return receiveJson(inBufferedReader.readLine());
  }

  public static String receiveJson(String jsonMessage) throws JSONException {
    printMessage(clientClassName(), "<- " + jsonMessage);

    JSONObject obj = new JSONObject(jsonMessage);
    return obj.toString();
  }

  public static JSONObject jsonType(String message) throws JSONException {
    JSONObject json = new JSONObject();
    json.put(Messages.JSON_TYPE, message);

    return json;
  }

  public static String sendJsonType(
      String type, PrintWriter outPrintWriter, BufferedReader inBufferedReader)
      throws JSONException, IOException {
    return sendJson(jsonType(type), outPrintWriter, inBufferedReader);
  }

  public static String readJsonType(String jsonMessage) throws JSONException {
    JSONObject obj = new JSONObject(jsonMessage);
    return obj.getString(Messages.JSON_TYPE);
  }

  public static ClientThreadState getStateFromTurnInit(String jsonMessage) throws JSONException {
    JSONObject obj = new JSONObject(jsonMessage);

    JSONObject gameState = obj.getJSONObject(Messages.JSON_GAMESTATE);

    String turn = gameState.getString(Messages.JSON_TYPE_TURN);

    if (turn.equals(Messages.JSON_TYPE_YOUR_TURN)) {
      printMessage(clientClassName(), "It's your turn");
      return ClientThreadState.SERVER_LISTENING;
    } else if (turn.equals(Messages.JSON_TYPE_WAIT_TURN)) {
      printMessage(clientClassName(), "Wait for your turn");
      return ClientThreadState.CLIENT_LISTENING;
    } else {
      return ClientThreadState.ERROR;
    }
  }
}
