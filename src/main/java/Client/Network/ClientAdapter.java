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
        // Setting up connection
        setup(host, port);

        // Greeting the server
        greetings();

        // Saying goodbye to the server
        exit();
    }

    public void greetings() {
        String answer = sendJsonMessage(Messages.CLIENT_HELLO);

        if (answer.equals(Messages.SERVER_HELLO_ANS)) {
            printMessage("Successful connection to the server: ");
        } else {
            printMessage("Unexpected server answer: '"  + answer + "'. Expected '" + Messages.SERVER_HELLO_ANS + "'");
            exit();
        }
    }

    public void exit() {
        String answer = sendJsonMessage(Messages.CLIENT_GOODBYE);
        cleanupResources();

        if (answer.equals(Messages.SERVER_GOODBYE_ANS)) {
            printMessage("Exiting as expected");
            System.exit(0);
        } else {
            printMessage("Unexpected server answer: '"  + answer + "'. Expected '" + Messages.SERVER_GOODBYE_ANS + "'");
            System.exit(1);
        }
    }

    public void setup(String host, int port) {
        this.host = host;
        this.port = port;

        try {
            clientSocket = new Socket(host, port);
            inBufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outPrintWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (UnknownHostException e) {
            printError("Unknown host error", e);
        } catch (IOException e) {
            printError("IO Error", e);
        }
    }

    public void cleanupResources() {
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

            return readJsonMessage(inBufferedReader.readLine());

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

        String message;

        try {

            JSONObject obj = new JSONObject(jsonMessage);
            message = obj.getString("message");

            printMessage("<- " + message);
            return message;

        } catch (JSONException e) {
            printMessage("Answer was not Json!");
            printMessage(e.toString());
            return "Error";
        }
    }

    /***********************************************************************************************************
     *                                        END OF UTILITIES                                                 *
     **********************************************************************************************************/
}