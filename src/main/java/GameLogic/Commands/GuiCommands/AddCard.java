package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Commands.CommandName;
import Network.Messages;
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
        // Todo : execution on the GUI
    }

    @Override
    public void undo(GameBoard gameBoard) {
        // Todo : undo on the GUI
    }
}