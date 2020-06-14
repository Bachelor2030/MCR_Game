package Network;

public class ServerLauncher {
    public static void main(String[] args) {
        ServerAdapter server = new ServerAdapter(2205, 4, 12);
        server.serveClients();
    }
}