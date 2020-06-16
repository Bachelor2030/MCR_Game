package gameLogic.commands.guiCommands;

import gui.GameBoard;
import gameLogic.board.Spot;
import gameLogic.receptors.Creature;
import gameLogic.commands.CommandName;
import gameLogic.receptors.Receptor;
import network.Messages;

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
            place.put(Messages.JSON_TYPE_CARD_ID, cardID);
            place.put(Messages.JSON_TYPE_POSITION, position.toJson());
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