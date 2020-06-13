package GameLogic.Commands.GuiCommands;

import GUI.GameBoard;
import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;
import GameLogic.Board.Position;

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
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\" :\"" + name + "\", \"player\" : \"" + playerName + "\"");

        sb.append(", \"positionTo\" : { \"line\" : " +
                to.getBoardLine().getNoLine() +
                ", \"spot\" : " +
                to.getPosition() +
                "}");

        sb.append(", \"positionFrom\" : { \"line\" : " +
                from.getBoardLine().getNoLine() +
                ", \"spot\" : " +
                from.getPosition() +
                "}}");

        return sb.toString();
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
