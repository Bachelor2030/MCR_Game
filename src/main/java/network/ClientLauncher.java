package network;

import gui.GameBoard;

/**
 * Classe non utilisée permettant de lancer un clientAdapter manuellement
 */
public class ClientLauncher {
  public static void main(String[] args) {
    ClientAdapter client =
        new ClientAdapter(new GameBoard(), args[0], Integer.valueOf(args[1]), args[2]);
    client.run();
    System.out.println("Hello, world!");
  }
}
