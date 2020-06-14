package gameLogic.Commands.GuiCommands;

import gui.GameBoard;
import gameLogic.Commands.CommandName;
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
            addCard.put("cardid", cardID);
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