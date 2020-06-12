package Common.Network.Utilities;

import Server.Network.ServerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static Common.Network.Utilities.Info.printMessage;
import static Common.Network.Utilities.Info.servantNameForPlayer;

public abstract class Streams {

    public static void cleanUpResourcesError(String className, Exception ex, BufferedReader inBufferedReader, PrintWriter outPrintWriter, Socket clientSocket) {
        if (inBufferedReader != null) {
            try {
                inBufferedReader.close();
            } catch (IOException ex1) {
                printMessage(Info.MessageLevel.Error, className, ex1.getMessage());
            }
        }
        if (outPrintWriter != null) {
            outPrintWriter.close();
        }
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException ex1) {
                printMessage(Info.MessageLevel.Error, className, ex1.getMessage());
            }
        }
        printMessage(Info.MessageLevel.Error, className, ex.getMessage());
    }

    public static void cleanupResources(String className, BufferedReader inBufferedReader, PrintWriter outPrintWriter, Socket clientSocket) throws IOException {
        printMessage(className, "Cleaning up resources...");

        clientSocket.close();
        inBufferedReader.close();
        outPrintWriter.close();
    }

}
