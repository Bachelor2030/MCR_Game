package gameLogic.commands.guiCommands;

import gui.GameBoard;
import gameLogic.board.Spot;
import gameLogic.receptors.Creature;
import gameLogic.commands.CommandName;
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
        // Todo : execution on the gui
        Creature creature = (Creature)gameBoard
                .getGUIBoard()
                .getLine(from.getLineNumber())
                .getSpot(from.getSpotNumber())
                .getOccupant();
        gameBoard.place(new Creature("empty", 0, 0,0), from.getLineNumber(), from.getSpotNumber());
        gameBoard.place(creature, to.getLineNumber(), to.getSpotNumber());
    }

    @Override
    public void undo(GameBoard gameBoard) {
        // Todo : undo on the gui
        Creature creature = (Creature)gameBoard
                .getGUIBoard()
                .getLine(to.getLineNumber())
                .getSpot(to.getSpotNumber())
                .getOccupant();
        gameBoard.place(new Creature("empty", 0, 0,0), to.getLineNumber(), to.getSpotNumber());
        gameBoard.place(creature, from.getLineNumber(), from.getSpotNumber());
    }
}
