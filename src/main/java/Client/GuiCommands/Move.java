package Client.GuiCommands;

import Client.View.GameBoard;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.Position;

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
        sb.append("{\"type\" : \"GUI Command\", \"name\"" + name + "\", \"player\" : " + playerName);

        sb.append(", \"positionTo\" : { \"line\" : " +
                to.getBoardLine() +
                ", \"spot\" : " +
                to.getPosition() +
                "}");

        sb.append(", \"positionFrom\" : { \"line\" : " +
                from.getBoardLine() +
                ", \"spot\" : " +
                from.getPosition() +
                "}}");

        return sb.toString();
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
