package network.states;

/**
 * Différents états dans lesquels le thread client peut se trouver
 */
public enum ClientThreadState {
  CLIENT_LISTENING,
  SERVER_LISTENING,
  GAME_ENDED,
  ERROR
}
