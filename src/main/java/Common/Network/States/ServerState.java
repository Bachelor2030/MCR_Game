package Common.Network.States;

public class ServerState {

    private WorkerState[] workerStates;
    private String[] playerNames;
    private PlayState[] playStates;

    private int playingNowId;
    private int playerCount = 0;

    public ServerState(int playingFirstId) {
        workerStates = new WorkerState[2];
        workerStates[workerId(1)] = WorkerState.CONNECTING;
        workerStates[workerId(2)] = WorkerState.CONNECTING;

        playerNames = new String[2];
        playerNames[workerId(1)] = null;
        playerNames[workerId(2)] = null;

        playStates = new PlayState[2];
        playStates[workerId(1)] = PlayState.WAIT_PLAY;
        playStates[workerId(2)] = PlayState.WAIT_PLAY;

        playingNowId = playingFirstId;
    }

    public synchronized WorkerState getWorkerState(int playerId) {
        return workerStates[workerId(playerId)];
    }

    public synchronized void setPlayStates(int playerId, PlayState playState) {
        playStates[workerId(playerId)] = playState;
    }

    public synchronized PlayState getPlayStates(int playerId) {
        return playStates[workerId(playerId)];
    }

    public synchronized void setWorkerState(int playerId, WorkerState workerState) {
        this.workerStates[workerId(playerId)] = workerState;
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

    public void setPlayerName(int playerId, String playerName) {
        playerNames[workerId(playerId)] = playerName;
    }

    public String getPlayerName(int playerId) {
        return playerNames[workerId(playerId)];
    }

    private int workerId(int playerId) {
        return playerId - 1;
    }

}
