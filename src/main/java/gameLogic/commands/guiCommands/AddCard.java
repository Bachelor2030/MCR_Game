package gameLogic.commands.guiCommands;

import gameLogic.commands.CommandName;
import gui.GameBoard;
import network.Messages;
import network.jsonUtils.GUIParser;
import org.json.JSONException;
import org.json.JSONObject;

public class AddCard extends GuiCommand {
  private int cardID;

  public AddCard() {
    super(CommandName.ADD_CARD);
  }

  public void setCardID(int cardID) {
    this.cardID = cardID;
  }

  @Override
  public JSONObject toJson() {
    JSONObject addCard = super.toJson();

    try {
      addCard.put(Messages.JSON_TYPE_CARD_ID, cardID);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return addCard;
  }

  @Override
  public void execute(GameBoard gameBoard) {
    gameBoard.addCard(GUIParser.getCardFromId(cardID));
  }

  @Override
  public void undo(GameBoard gameBoard) {
    gameBoard.removeCard(cardID);
  }
}
