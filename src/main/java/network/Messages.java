package network;

/**
 * Classe regroupant les strings composant les messages json
 */
public final class Messages {

  public static final String JSON_TYPE = "type";


  public static final String JSON_TYPE_PLAY = "play";
  public static final String JSON_TYPE_UPDATE = "update";
  public static final String JSON_TYPE_UPDATE_END = "update end";
  public static final String JSON_TYPE_END_TURN = "end turn";
  public static final String JSON_TYPE_ABANDON = "abandon";
  public static final String JSON_TYPE_UNDO = "undo";


  public static final String JSON_GAMESTATE = "Game state";

  public static final String JSON_TYPE_UNKNOWN = "Nani?";

  public static final String JSON_TYPE_HELLO = "Hello";
  public static final String JSON_TYPE_WAIT_PLAYER = "Waiting for Player 2";
  public static final String JSON_TYPE_INIT = "Init";

  public static final String JSON_TYPE_GOODBYE = "Goodbye";
  public static final String JSON_TYPE_GOODBYE_ANS = "Goodbye";
  public static final String JSON_TYPE_GAME_END = "Game end";
  public static final String JSON_TYPE_GAME_START = "Game start";

  public static final String JSON_TYPE_PLAYERNAME = "Player name";
  public static final String JSON_TYPE_ENEMY_NAME = "enemy name";

  public static final String JSON_TYPE_TURN = "turn";
  public static final String JSON_TYPE_WAIT_TURN = "Wait turn";
  public static final String JSON_TYPE_YOUR_TURN = "Your turn";

  public static final String JSON_TYPE_PLAY_OK = "play ok";
  public static final String JSON_TYPE_PLAY_BAD = "play bad";

  public static final String JSON_TYPE_LINE = "line";
  public static final String JSON_TYPE_SPOT = "spot";
  public static final String JSON_TYPE_CARDS = "cards";
  public static final String JSON_TYPE_EFFECT = "effect";
  public static final String JSON_TYPE_POSITION = "position";
  public static final String JSON_TYPE_NAME = "name";
  public static final String JSON_TYPE_IMAGE = "img";
  public static final String JSON_TYPE_OWNER = "owner";

  public static final String JSON_TYPE_LP = "life";
  public static final String JSON_TYPE_MP = "steps";
  public static final String JSON_TYPE_AP = "attack";
  public static final String JSON_TYPE_DESCRIPTION = "description";
  public static final String JSON_TYPE_CARD_ID = "id";
  public static final String JSON_TYPE_COMMANDS = "commands";
  public static final String JSON_TYPE_COST = "cost";
  public static final String JSON_TYPE_PLAYER = "Player";
  public static final String JSON_TYPE_RECEPTOR = "live receptor";
  public static final String JSON_TYPE_POSITION_TO = "to";
  public static final String JSON_TYPE_POSITION_FROM = "from";
  public static final String JSON_TYPE_ENEMY_IMAGE = "enemy image";
  public static final String JSON_TYPE_COMMAND = "command";
  public static final String JSON_TYPE_POINTS_TYPE = "point type";
  public static final String JSON_TYPE_CREATURE = "creature";
  public static final String JSON_TYPE_LOSE_WIN = "end game status";

    private Messages() {
    throw new IllegalStateException("Can't instantiate the messages class");
  }
}
