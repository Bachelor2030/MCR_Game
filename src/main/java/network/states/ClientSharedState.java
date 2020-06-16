package network.states;

public class ClientSharedState {

    private String enemyName, enemyImagePath;
    private String playerName;
    private boolean finishedInit = false;

    public synchronized String getEnemyName() {
        return enemyName;
    }

    public synchronized String getEnemyImagePath() {
        return enemyImagePath;
    }

    public synchronized void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public synchronized String getPlayerName() {
        return playerName;
    }

    public synchronized void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public synchronized void setEnemyImagePath(String enemyImagePath) {
        this.enemyImagePath = enemyImagePath;
    }

    public synchronized boolean isFinishedInit() {
        return finishedInit;
    }

    public synchronized void setFinishedInit(boolean finishedInit) {
        this.finishedInit = finishedInit;
    }
}
