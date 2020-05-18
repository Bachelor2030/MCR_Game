package Client.Network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
    BufferedReader in = null;
    PrintWriter out = null;


    public ClientAdapter(String host, int port) {
        // Network stuff
        this.host = host;
        this.port = port;

        // Setting up connection
        try {
            clientSocket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
        } catch (UnknownHostException e) {
            System.out.println("Unknown host error");
        } catch (IOException e) {
            System.out.println("IO error");
        }

        // Greetings
        String msg = sendMessage("Moshimoshi");
        if (!msg.equals("Domo hajimemashite")) {
            displayError("Server answer error: weeb not found");
        }
    }

    private void displayError(String error) {

    }

    public String sendMessage(String request) {
        try {
            out.println(request);
            out.flush();
            return in.readLine();
        } catch (IOException e) {
            return "IO error";
        }
    }

    public void exit() {
        sendMessage("Sayonara");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}