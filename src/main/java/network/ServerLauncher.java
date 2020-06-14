package network;

public class ServerLauncher {
    public static void main(String[] args) {
        ServerAdapter server = new ServerAdapter(2205, 1);
        server.serveClients();
    }
}