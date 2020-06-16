package network.states;

public class ClientSharedState {

    private String enemyName;
    private String playerName;
    private boolean finishedInit = false;

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isFinishedInit() {
        return finishedInit;
    }

    public void setFinishedInit(boolean finishedInit) {
        this.finishedInit = finishedInit;
    }
}
