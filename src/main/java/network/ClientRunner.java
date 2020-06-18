package network;

/**
 * Classe permettant de rendre le client Runnable sur un nouveau thread
 * pour libérer le thread de l'application graphique
 */
public class ClientRunner implements Runnable {
  private ClientAdapter clientAdapter;

  public ClientRunner(ClientAdapter clientAdapter) {
    this.clientAdapter = clientAdapter;
  }

  @Override
  public void run() {
    clientAdapter.run();
  }
}
