package Client.Network;

public class ClientLauncher {
    public static void main(String[] args) {
        ClientAdapter client = new ClientAdapter("localhost", 2205);
    }
}