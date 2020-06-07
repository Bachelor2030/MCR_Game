package Client.Network;

import Common.Network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientAdapter {

    final static int BUFFER_SIZE = 1024;
    String host;
    int port;

    Socket clientSocket = null;
    BufferedReader inBufferedReader = null;
    PrintWriter outPrintWriter = null;


    public ClientAdapter(String host, int port) {
        // Network stuff
        this.host = host;
        this.port = port;

        // Setting up connection
        try {
            clientSocket = new Socket(host, port);
            inBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outPrintWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (UnknownHostException e) {
            printError("Unknown host error", e);
        } catch (IOException e) {
            printError("IO Error", e);
        }

        // Greetings
        String answer = sendJsonMessage(Messages.CLIENT_HELLO);

        exit();
    }

    /***********************************************************************************************************
     *                                           UTILITIES                                                     *
     **********************************************************************************************************/

    private void printError(String message, Exception e) {
        System.out.println(message);
        System.out.println(e);
    }

    private void printMessage(String msg) {
        System.out.println(msg);
    }

    private String sendJsonMessage(String message) {
        printMessage("-> " + message);
        try {
            sendJson(jsonMessage(message));

            String ret = readJsonMessage(inBufferedReader.readLine());

            return ret;

        } catch (IOException | JSONException e) {
            return "IO error";
        }
    }

    private void sendJson(JSONObject json) {
        outPrintWriter.println(json.toString());
        outPrintWriter.flush();
    }

    private JSONObject jsonMessage(String message) throws JSONException {

        JSONObject json = new JSONObject();
        json.put("message", message);

        return json;
    }

    private String readJsonMessage(String jsonMessage) {

        String message = "Unprocessed";

        try {

            JSONObject obj = new JSONObject(jsonMessage);
            message = obj.getString("message");

        } catch (JSONException e) {
            printMessage("Answer was not Json!");
            printMessage(e.toString());
            message = "Error";
        } finally {

            printMessage("<- " + message);
            return message;

        }
    }

    /***********************************************************************************************************
     *                                        END OF UTILITIES                                                 *
     **********************************************************************************************************/

    public void exit() {
        sendJsonMessage(Messages.CLIENT_GOODBYE);
        try {
            inBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outPrintWriter.close();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}