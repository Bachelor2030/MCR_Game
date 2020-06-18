package network.utilities;

import network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;

import static network.utilities.Info.printMessage;

/**
 * Classe utile pour envoyer du json depuis le serveur.
 * Différente de la classe client car les méthodes n'attendent pas de réponse.
 */
public abstract class JsonServer {

  /**
   * Envoi du json au client
   * @param jsonObject L'object json à envoyer
   * @param outPrintWriter L'outPrintWriter dans lequel écrire le message
   * @param className Le nom de la classe qui appelle la fonction
   */
  public static void sendJson(JSONObject jsonObject, PrintWriter outPrintWriter, String className) {
    printMessage(className, "-> " + jsonObject.toString());
    outPrintWriter.println(jsonObject.toString());
    outPrintWriter.flush();
  }

  /**
   * Envoie directement sur le réseau un simple message de type
   * @param message Le message correspondant au type
   * @param outPrintWriter L'outPrintWriter dans lequel écrire le message
   * @param className Le nom de la classe qui appelle la fonction
   * @throws JSONException Si la création du json échoue
   */
  public static void sendJsonType(String message, PrintWriter outPrintWriter, String className)
      throws JSONException {
    JSONObject json = new JSONObject();
    json.put(Messages.JSON_TYPE, message);

    sendJson(json, outPrintWriter, className);
  }

/**
 * Prend un objet json et retourne sa représentation en string
 * @param jsonMessage Le message json
 * @param className Le nom de la classe appelante
 */
  public static String readJson(String jsonMessage, String className) {
    printMessage(className, "<- " + jsonMessage);
    try {
      JSONObject obj = new JSONObject(jsonMessage);
      return obj.toString();
    } catch (JSONException e) {
      return jsonError(className);
    }
  }

  /**
   * Extrait le type d'un message json
   * @param jsonMessage Le message json
   * @param className Le nom de la classe appelante
   * @return Le type extrait
   */
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

  /**
   * Extrait le nom d'un joueur d'un message json
   * @param jsonMessage Le message json
   * @param className Le nom de la classe appelante
   * @return Le nom du joueur extrait, ou error s'il n'y en a pas
   */
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

  /**
   * Affiche un message d'erreur json et renvoie une  string "Error"
   * @param className Le nom de la classe appelante
   * @return La string "Error"
   */
  public static String jsonError(String className) {
    printMessage(className, "Answer was not Json!");

    return "Error";
  }
}
