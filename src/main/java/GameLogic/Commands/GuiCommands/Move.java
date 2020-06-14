package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
import GameLogic.Board.Position;
import org.json.JSONException;
import org.json.JSONObject;

public class Move extends GuiCommand {
    private Position from, to;

    public Move() {
        super(CommandName.MOVE);
    }

    public void setTo(Position to) {
        this.to = to;
    }

    public void setFrom(Position from) {
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
                .getBoard()
                .getLine(from.getBoardLine().getNoLine())
                .getSpot(from.getPosition())
                .getOccupant();
        gameBoard.place(new Creature("empty", 0, 0,0), from.getBoardLine().getNoLine(), from.getPosition());
        gameBoard.place(creature, to.getBoardLine().getNoLine(), to.getPosition());
    }

    @Override
    public void undo(GameBoard gameBoard) {
        // Todo : undo on the GUI
        Creature creature = (Creature)gameBoard
                .getBoard()
                .getLine(to.getBoardLine().getNoLine())
                .getSpot(to.getPosition())
                .getOccupant();
        gameBoard.place(new Creature("empty", 0, 0,0), to.getBoardLine().getNoLine(), to.getPosition());
        gameBoard.place(creature, from.getBoardLine().getNoLine(), from.getPosition());
    }
}
