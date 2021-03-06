package network.states;

import gameLogic.Game;
import network.ServerAdapter;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Etat partagé entre les threads servantWorker et la classe logique du backend
 */
public class ServerSharedState {
  private ServerThreadState[] serverThreadStates;
  private String[] playerNames;
  private LinkedList<JSONObject>[] jsonToSend;
  private boolean[] intendToSendJson;

  private boolean gameEnded = false;
  private boolean finishedInit;
  private int playingNowId;
  private final int playingFirstId;
  private int playerCount = 0;
  private Game game;

  /**
   * Constructeur de ServerSharedState
   * @param playingFirstId L'id du joueur jouant en premier
   * @param game La classe gérant la logique du jeu
   */
  public ServerSharedState(int playingFirstId, Game game) {
    serverThreadStates = new ServerThreadState[2];
    serverThreadStates[0] = ServerThreadState.CONNECTING;
    serverThreadStates[1] = ServerThreadState.CONNECTING;

    playerNames = new String[2];
    playerNames[0] = null;
    playerNames[1] = null;

    jsonToSend = new LinkedList[2];
    jsonToSend[0] = new LinkedList<>();
    jsonToSend[1] = new LinkedList<>();

    intendToSendJson = new boolean[2];

    finishedInit = false;
    playingNowId = playingFirstId;
    this.playingFirstId = playingFirstId;
    this.game = game;
  }

  public synchronized int getPlayingFirstId() {
    return playingFirstId;
  }

  public synchronized boolean gameEnded() {
    return gameEnded;
  }

  public synchronized void endGame() {
    gameEnded = true;
  }

  public synchronized boolean finishedInit() {
    return finishedInit;
  }

  public synchronized void setFinishedInit() {
    this.finishedInit = true;
  }

  public synchronized void setIntendToSendJson(int playerId, boolean intent) {
    intendToSendJson[workerId(playerId)] = intent;
  }

  public synchronized boolean getIntendToSendJson(int playerId) {
    return intendToSendJson[workerId(playerId)];
  }

  public synchronized boolean jsonToSendEmpty(int playerId) {
    return jsonToSend[workerId(playerId)].size() == 0;
  }

  public synchronized void pushJsonToSend(JSONObject json, int playerId) {
    jsonToSend[workerId(playerId)].push(json);
  }

  public synchronized JSONObject popJsonToSend(int playerId) {
    return jsonToSend[workerId(playerId)].pop();
  }

  public synchronized ServerThreadState getWorkerState(int playerId) {
    return serverThreadStates[workerId(playerId)];
  }

  public synchronized void setWorkerState(int playerId, ServerThreadState serverThreadState) {
    this.serverThreadStates[workerId(playerId)] = serverThreadState;
  }

  public synchronized int getPlayerCount() {
    return playerCount;
  }

  public synchronized int getPlayingId() {
    return playingNowId;
  }

  public synchronized void nextPlayer() {
    playingNowId = playingNowId % 2 + 1;
    if (playingNowId == playingFirstId) {
      game.nextTurn();
    }
  }

  public synchronized void incrementPlayerCount() {
    ++playerCount;
  }

  public synchronized void decrementPlayerCount() {
    --playerCount;
  }

  public int otherPlayer(int playerId) {
    return playerId % 2 + 1;
  }

  public synchronized void setPlayerName(int playerId, String playerName) {
    playerNames[workerId(playerId)] = playerName;
  }

  public synchronized String getPlayerName(int playerId) {
    return playerNames[workerId(playerId)];
  }

  public synchronized boolean playerNamesSet() {
    return playerNames[workerId(1)] != null && playerNames[workerId(2)] != null;
  }

  private int workerId(int playerId) {
    return playerId - 1;
  }
}
