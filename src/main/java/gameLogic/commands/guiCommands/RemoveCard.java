package gameLogic.commands.guiCommands;

import gameLogic.commands.CommandName;
import gui.GameBoard;
import network.Messages;
import network.jsonUtils.GUIParser;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoveCard extends GuiCommand {
  private int cardID;

  public RemoveCard() {
    super(CommandName.REMOVE_CARD);
  }

  public void setCardID(int cardID) {
    this.cardID = cardID;
  }

  @Override
  public JSONObject toJson() {
    JSONObject removeCard = super.toJson();

    try {
      removeCard.put(Messages.JSON_TYPE_CARD_ID, cardID);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return removeCard;
  }

  @Override
  public void execute(GameBoard gameBoard) {
    gameBoard.removeCard(cardID);
  }

  @Override
  public void undo(GameBoard gameBoard) {
    gameBoard.addCard(GUIParser.getCardFromId(cardID));
  }
}
