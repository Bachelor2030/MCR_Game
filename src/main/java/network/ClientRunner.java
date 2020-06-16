package network;

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
