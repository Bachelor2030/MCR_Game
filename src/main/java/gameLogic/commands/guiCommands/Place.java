package gameLogic.commands.guiCommands;

import gameLogic.commands.CommandName;
import gui.GameBoard;
import gui.board.GUISpot;
import gui.receptors.GUICreature;
import gui.receptors.GUIReceptor;
import gui.receptors.GUITrap;
import network.Messages;
import network.jsonUtils.GUIParser;
import org.json.JSONException;
import org.json.JSONObject;

public class Place extends GuiCommand {
  private GUISpot position;
  private int cardID;
  private GUIReceptor receptor;

  public Place() {
    super(CommandName.PLACE);
  }

  public void setCardID(int cardID) {
    this.cardID = cardID;
    receptor = GUIParser.getCreatureFromCard(cardID);
  }

  public void setPosition(GUISpot position) {
    this.position = position;
  }

  @Override
  public JSONObject toJson() {
    JSONObject place = super.toJson();

    try {
      place.put(Messages.JSON_TYPE_CARD_ID, cardID);
      place.put(Messages.JSON_TYPE_POSITION, position.toJson());
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return place;
  }

  @Override
  public void execute(GameBoard gameBoard) {
    if (receptor.getClass() == GUITrap.class) {
      gameBoard.placeTrap(position.getLineNumber(), position.getSpotNumber());
    } else {
      gameBoard.place(receptor, position.getLineNumber(), position.getSpotNumber());
    }
  }

  @Override
  public void undo(GameBoard gameBoard) {
    if (receptor.getClass() == GUITrap.class) {
      gameBoard.removeTrap(position.getLineNumber(), position.getSpotNumber());
    } else {
      gameBoard.place(new GUICreature(), position.getLineNumber(), position.getSpotNumber());
    }
  }
}
