package network;

/**
 * Classe non utilisée permettant de lancer un clientLauncher manuellement
 */
public class ServerLauncher {
  public static void main(String[] args) {
    ServerAdapter server = new ServerAdapter(Integer.valueOf(args[0]), 4, 12);
    server.serveClients();
  }
}
