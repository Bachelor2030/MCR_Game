package network;

public class ClientLauncher {
    public static void main(String[] args) {
        ClientAdapter client = new ClientAdapter(args[0], Integer.valueOf(args[1]), args[2]);
        client.run();
        System.out.println("Hello, world!");
    }
}