package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
import GameLogic.Board.Position;
import org.json.JSONException;
import org.json.JSONObject;

public class Place extends GuiCommand {
    private Position position;
    private int cardID;
    private Creature creature;

    public Place() {
        super(CommandName.PLACE);
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
        // TODO change that
        creature = null;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public JSONObject toJson() {
        JSONObject place = super.toJson();

        try {
            place.put("cardid", cardID);
            place.put("position", position.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return place;
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