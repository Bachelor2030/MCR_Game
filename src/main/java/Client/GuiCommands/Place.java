package Client.GuiCommands;

import Client.View.GameBoard;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.Position;

public class Place extends GuiCommand {
    private Position position;
    private int cardID;

    public Place() {
        super(CommandName.PLACE);
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\"" + name + "\", \"player\" : " + playerName);

        sb.append(", \"cardID\" : " + cardID);

        sb.append(", \"position\" : { \"line\" : " +
                position.getLine() +
                ", \"spot\" : " +
                position.getPosition() +
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