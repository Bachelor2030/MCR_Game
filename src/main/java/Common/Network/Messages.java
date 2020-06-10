package Common.Network;

public final class Messages {

    public static final String CLIENT_HELLO = "Hello server";
    public static final String SERVER_HELLO_ANS = "Hello client";
    public static final String CLIENT_GOODBYE = "Goodbye server";
    public static final String SERVER_GOODBYE_ANS = "Goodbye client";

    public static final String SERVER_UNKNOWN_ANS = "Nani?";

    private Messages() {
        throw new IllegalStateException("Can't instantiate the messages class");
    }
}