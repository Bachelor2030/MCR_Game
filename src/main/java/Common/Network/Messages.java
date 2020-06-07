package Common.Network;

public final class Messages {

    public static final String CLIENT_HELLO = "Moshimoshi";
    public static final String SERVER_HELLO_ANS = "Domo hajimemashite";
    public static final String CLIENT_GOODBYE = "Sayonara";
    public static final String SERVER_GOODBYE_ANS = "O-genki de";

    private Messages() {
        throw new IllegalStateException("Can't instantiate the messages class");
    }
}