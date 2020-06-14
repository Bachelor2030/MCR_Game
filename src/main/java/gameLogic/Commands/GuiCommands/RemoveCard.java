package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Commands.CommandName;
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
            removeCard.put("cardid", cardID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return removeCard;
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