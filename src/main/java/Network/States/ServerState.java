package Network.States;

import org.json.JSONObject;

import java.util.LinkedList;

public class ServerState {

    private WorkerState[] workerStates;
    private String[] playerNames;
    private PlayState[] playStates;
    private LinkedList<JSONObject>[] jsonToSend;
    private boolean[] intendToSendJson;

    private boolean finishedInit;
    private int playingNowId;
    private int playerCount = 0;

    public ServerState(int playingFirstId) {
        workerStates = new WorkerState[2];
        workerStates[0] = WorkerState.CONNECTING;
        workerStates[1] = WorkerState.CONNECTING;

        playerNames = new String[2];
        playerNames[0] = null;
        playerNames[1] = null;

        playStates = new PlayState[2];
        playStates[0] = PlayState.WAIT_PLAY;
        playStates[1] = PlayState.WAIT_PLAY;

        jsonToSend = new LinkedList[2];
        jsonToSend[0] = new LinkedList<>();
        jsonToSend[1] = new LinkedList<>();

        intendToSendJson = new boolean[2];

        finishedInit = false;
        playingNowId = playingFirstId;
    }

    public synchronized boolean finishedInit() {
        return finishedInit;
    }

    public synchronized void setFinishedInit() {
        this.finishedInit = true;
    }

    public synchronized boolean intendToSendJson(int playerId) {
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

    public synchronized WorkerState getWorkerState(int playerId) {
        return workerStates[workerId(playerId)];
    }

    public synchronized void setWorkerState(int playerId, WorkerState workerState) {
        this.workerStates[workerId(playerId)] = workerState;
    }

    public synchronized void setPlayStates(int playerId, PlayState playState) {
        playStates[workerId(playerId)] = playState;
    }

    public synchronized PlayState getPlayStates(int playerId) {
        return playStates[workerId(playerId)];
    }

    public synchronized int getPlayerCount() {
        return playerCount;
    }

    public synchronized int getPlayingId() {
        return playingNowId;
    }

    public synchronized void nextPlayer() {
        playingNowId = playingNowId % 2 + 1;
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
