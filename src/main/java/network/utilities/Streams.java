package network.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static network.utilities.Info.printMessage;

public abstract class Streams {
  /**
   * Ferme les différents streams et objets ouverts par le serveur
   * @param className Le nom de la classe appelante
   * @param inBufferedReader Le inBufferedReader dans lequel lire le message du client
   * @param outPrintWriter Le outPrintWriter dans lequel répondre au client
   * @param clientSocket La socket utilisée pour se connecter au client
   */
  public static void cleanupResources(
      String className,
      BufferedReader inBufferedReader,
      PrintWriter outPrintWriter,
      Socket clientSocket) {
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
