package gameLogic.commands.guiCommands;

import gui.GameBoard;
import gameLogic.board.Spot;
import gameLogic.commands.CommandName;
import gui.receptors.GUICreature;
import network.Messages;

import org.json.JSONException;
import org.json.JSONObject;

public class Move extends GuiCommand {
    private Spot from, to;

    public Move() {
        super(CommandName.MOVE);
    }

    public void setTo(Spot to) {
        this.to = to;
    }

    public void setFrom(Spot from) {
        this.from = from;
    }

    @Override
    public JSONObject toJson() {
        JSONObject move = super.toJson();

        try {
            move.put(Messages.JSON_TYPE_POSITION_FROM, from.toJson());
            move.put(Messages.JSON_TYPE_POSITION_TO, to.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return move;
    }

    @Override
    public void execute(GameBoard gameBoard) {
        GUICreature creature = (GUICreature)gameBoard
                .getGUIBoard()
                .getLine(from.getLineNumber())
                .getSpot(from.getSpotNumber())
                .getOccupant();
        gameBoard.place(new GUICreature(), from.getLineNumber(), from.getSpotNumber());
        gameBoard.place(creature, to.getLineNumber(), to.getSpotNumber());
    }

    @Override
    public void undo(GameBoard gameBoard) {
        GUICreature creature = (GUICreature)gameBoard
                .getGUIBoard()
                .getLine(to.getLineNumber())
                .getSpot(to.getSpotNumber())
                .getOccupant();
        gameBoard.place(new GUICreature(), to.getLineNumber(), to.getSpotNumber());
        gameBoard.place(creature, from.getLineNumber(), from.getSpotNumber());
    }
}
