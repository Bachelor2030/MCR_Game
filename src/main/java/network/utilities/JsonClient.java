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

  /**
   * Envoie du json au serveur connecté et attend une réponse
   * @param jsonObject L'object json à envoyer
   * @param outPrintWriter L'outPrintWriter dans lequel écrire le message
   * @param inBufferedReader L'inBufferedReader dans lequel lire la réponse
   * @return Le message reçu du serveur
   * @throws IOException Si une IO erreur arrive
   * @throws JSONException si le json ne peut être parsé
   */
  public static String sendJson(
      JSONObject jsonObject, PrintWriter outPrintWriter, BufferedReader inBufferedReader)
      throws IOException, JSONException {
    printMessage(clientClassName(), "-> " + jsonObject.toString());
    outPrintWriter.println(jsonObject.toString());
    outPrintWriter.flush();

    return receiveJson(inBufferedReader.readLine());
  }

  /**
   * Prend un objet json et retourne sa représentation en string
   * @param jsonMessage
   * @return
   * @throws JSONException
   */
  public static String receiveJson(String jsonMessage) throws JSONException {
    printMessage(clientClassName(), "<- " + jsonMessage);

    JSONObject obj = new JSONObject(jsonMessage);
    return obj.toString();
  }

  /**
   * Rend un objet json contenant un simple type:message
   * @param message Le message correspondant au type
   * @return L'object json créé
   * @throws JSONException Si la création du json échoue
   */
  public static JSONObject jsonType(String message) throws JSONException {
    JSONObject json = new JSONObject();
    json.put(Messages.JSON_TYPE, message);

    return json;
  }

  /**
   * Envoie directement sur le réseau un simple message de type
   * @param type Le message correspondant au type
   * @param outPrintWriter L'outPrintWriter dans lequel écrire le message
   * @param inBufferedReader L'inBufferedReader dans lequel lire la réponse
   * @return Le message de réponse du serveur
   * @throws JSONException Si la création du json échoue
   * @throws IOException Si une IO erreur arrive
   */
  public static String sendJsonType(
      String type, PrintWriter outPrintWriter, BufferedReader inBufferedReader)
      throws JSONException, IOException {
    return sendJson(jsonType(type), outPrintWriter, inBufferedReader);
  }

  /**
   * Extrait le type d'un message json
   * @param jsonMessage Le message json
   * @return Le type extrait
   * @throws JSONException Si la clé "type" ne peut être trouvée
   */
  public static String readJsonType(String jsonMessage) throws JSONException {
    JSONObject obj = new JSONObject(jsonMessage);
    return obj.getString(Messages.JSON_TYPE);
  }

  /**
   * Détermine l'état d'un thread client en fonction du message d'init
   * @param jsonMessage Le message json d'init
   * @return Le ClientThreadState correspondant
   * @throws JSONException Si le jsonMessage ne contient pas ce qui est attendu
   */
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
