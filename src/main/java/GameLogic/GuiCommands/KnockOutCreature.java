package GameLogic.GuiCommands;

import GUI.GameBoard;
import GameLogic.ModelClasses.Commands.CommandName;
import GameLogic.ModelClasses.LiveReceptor;
import GameLogic.Position;

public class KnockOutCreature extends GuiCommand {
    private Position position;

    public KnockOutCreature() {
        super(CommandName.KNOCK_OUT_CREATURE);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\"" + name + "\", \"player\" : " + playerName);

        sb.append(", \"position\" : { \"line\" : " +
                position.getBoardLine() +
                ", \"spot\" : " +
                position.getPosition() +
                "}}");

        return sb.toString();
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