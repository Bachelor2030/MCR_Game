package network.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static network.utilities.Info.printMessage;


public abstract class Streams {
    public static void cleanupResources(String className, BufferedReader inBufferedReader, PrintWriter outPrintWriter, Socket clientSocket) {
        printMessage(className, "Cleaning up resources...");

        outPrintWriter.close();
        try {
            clientSocket.close();
            inBufferedReader.close();
        } catch (IOException e) {
            printMessage(Info.MessageLevel.Error, className, e.getMessage());
            e.printStackTrace();
        }
    }

}
