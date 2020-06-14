package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Board.Spot;
import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
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
            move.put("positionFrom", from.toJson());
            move.put("positionTo", to.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return move;
    }

    @Override
    public void execute(GameBoard gameBoard) {
        // Todo : execution on the GUI
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
        // Todo : undo on the GUI
        Creature creature = (Creature)gameBoard
                .getGUIBoard()
                .getLine(to.getLineNumber())
                .getSpot(to.getSpotNumber())
                .getOccupant();
        gameBoard.place(new Creature("empty", 0, 0,0), to.getLineNumber(), to.getSpotNumber());
        gameBoard.place(creature, from.getLineNumber(), from.getSpotNumber());
    }
}
