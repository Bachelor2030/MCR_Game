package Network;

public final class Messages {


    public static final String JSON_TYPE = "type";
    public static final String JSON_GAMESTATE = "Gamestate";

    public static final String JSON_TYPE_UNKNOWN = "Nani?";

    public static final String JSON_TYPE_HELLO = "Hello";
    public static final String JSON_TYPE_WAIT_PLAYER = "Waiting for player 2";
    public static final String JSON_TYPE_INIT = "Init";

    public static final String JSON_TYPE_GOODBYE = "Goodbye";
    public static final String JSON_TYPE_GOODBYE_ANS = "Goodbye";
    public static final String JSON_TYPE_GAME_END = "Game end";
    public static final String JSON_TYPE_GAME_START = "Game start";

    public static final String JSON_TYPE_PLAYERNAME = "playername";
    public static final String JSON_TYPE_ENEMYNAME = "enemyname";

    public static final String JSON_TYPE_TURN = "turn";
    public static final String JSON_TYPE_WAIT_TURN = "Wait turn";
    public static final String JSON_TYPE_YOUR_TURN = "Your turn";
    public static final String JSON_TYPE_PLAY = "play";

    public static final String JSON_TYPE_PLAY_OK = "play ok";
    public static final String JSON_TYPE_PLAY_BAD = "play bad";

    public static final String JSON_TYPE_LINE = "line";
    public static final String JSON_TYPE_SPOT = "spot";
    public static final String JSON_TYPE_CARDS = "cards";


    private Messages() {
        throw new IllegalStateException("Can't instantiate the messages class");
    }
}