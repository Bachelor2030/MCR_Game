package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Board.Spot;
import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
import GameLogic.Receptors.Receptor;
import org.json.JSONException;
import org.json.JSONObject;

public class Place extends GuiCommand {
    private Spot position;
    private int cardID;
    private Receptor receptor;

    public Place() {
        super(CommandName.PLACE);
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
        // TODO change that
        receptor = null;
    }

    public void setPosition(Spot position) {
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
        Receptor receptor = (Receptor)gameBoard
                .getGUIBoard()
                .getLine(position.getLineNumber())
                .getSpot(position.getSpotNumber())
                .getOccupant();
        gameBoard.place(receptor, position.getLineNumber(), position.getSpotNumber());
    }

    @Override
    public void undo(GameBoard gameBoard) {
        gameBoard.place(new Creature("empty", 0, 0, 0), position.getLineNumber(), position.getSpotNumber());
    }
}