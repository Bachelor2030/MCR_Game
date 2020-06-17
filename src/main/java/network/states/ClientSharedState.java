package network.states;

import gui.receptors.GUICard;

public class ClientSharedState {

    private GUICard selectedCard;
    private int[] chosenPosition;

    private String enemyName, enemyImagePath;
    private String playerName;
    private boolean finishedInit = false;

    private boolean myTurn = false;

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

    public synchronized void setChosenPosition(int[] chosenPosition) {
        this.chosenPosition = chosenPosition;
    }

    public synchronized void setSelectedCard(GUICard selectedCard) {
        this.selectedCard = selectedCard;
    }

    public synchronized GUICard getSelectedCard() {
        return selectedCard;
    }

    public synchronized int[] getChosenPosition() {
        return chosenPosition;
    }

    public synchronized boolean isMyTurn() {
        return myTurn;
    }

    public synchronized void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }
}
