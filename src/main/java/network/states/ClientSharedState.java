package network.states;

import gui.receptors.GUICard;
import org.json.JSONObject;

import java.util.LinkedList;

public class ClientSharedState {

  private GUICard selectedCard;
  private int[] chosenPosition;

  private LinkedList<JSONObject> jsonToSend;
  private boolean intendToSendJson = false;

  private String enemyName, enemyImagePath;
  private String playerName;
  private boolean finishedInit = false;

  private boolean myTurn = false;

  public synchronized String getEnemyName() {
    return enemyName;
  }

  public synchronized void setEnemyName(String enemyName) {
    this.enemyName = enemyName;
  }

  public synchronized boolean getIntendToSendJson() {
    return intendToSendJson;
  }

  public synchronized void setIntendToSendJson(boolean intent) {
    intendToSendJson = intent;
  }

  public synchronized boolean jsonToSendEmpty() {
    return jsonToSend.size() == 0;
  }

  public synchronized void pushJsonToSend(JSONObject json) {
    jsonToSend.push(json);
  }

  public synchronized JSONObject popJsonToSend() {
    return jsonToSend.pop();
  }

  public synchronized String getEnemyImagePath() {
    return enemyImagePath;
  }

  public synchronized void setEnemyImagePath(String enemyImagePath) {
    this.enemyImagePath = enemyImagePath;
  }

  public synchronized String getPlayerName() {
    return playerName;
  }

  public synchronized void setPlayerName(String playerName) {
    this.playerName = playerName;
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

  public synchronized GUICard getSelectedCard() {
    return selectedCard;
  }

  public synchronized void setSelectedCard(GUICard selectedCard) {
    this.selectedCard = selectedCard;
  }

  public synchronized boolean isMyTurn() {
    return myTurn;
  }

  public synchronized void setMyTurn(boolean myTurn) {
    this.myTurn = myTurn;
  }
}
