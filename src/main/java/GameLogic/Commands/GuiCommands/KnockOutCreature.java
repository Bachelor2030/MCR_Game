package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Commands.CommandName;
import GameLogic.Receptors.LiveReceptor;
import GameLogic.Board.Position;
import org.json.JSONException;
import org.json.JSONObject;

public class KnockOutCreature extends GuiCommand {
    private Position position;

    public KnockOutCreature() {
        super(CommandName.KNOCK_OUT_CREATURE);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public JSONObject toJson() {
        JSONObject knockout = super.toJson();

        try {
            knockout.put("position", position.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return knockout;
    }

    @Override
    public void execute(GameBoard gameBoard) {
        // Todo : execution on the GUI
        LiveReceptor receptor = (LiveReceptor)gameBoard
                .getBoard()
                .getLine(position.getBoardLine().getNoLine())
                .getSpot(position.getPosition())
                .getOccupant();
    }

    @Override
    public void undo(GameBoard gameBoard) {
        // Todo : undo on the GUI
        LiveReceptor receptor = (LiveReceptor)gameBoard
                .getBoard()
                .getLine(position.getBoardLine().getNoLine())
                .getSpot(position.getPosition())
                .getOccupant();
    }
}