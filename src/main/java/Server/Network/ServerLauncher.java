package Server.Network;

public class ServerLauncher {
    public static void main(String[] args) {
        ServerAdapter server = new ServerAdapter(2205);
        server.serveClients();
    }
}