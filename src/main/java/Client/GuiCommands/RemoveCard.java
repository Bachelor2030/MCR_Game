package Client.GuiCommands;

import Client.View.GameBoard;
import Server.Game.ModelClasses.Commands.CommandName;

public class RemoveCard extends GuiCommand {
    private int cardID;

    public RemoveCard() {
        super(CommandName.REMOVE_CARD);
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\"");
        sb.append(name);
        sb.append("\", \"player\" : ");
        sb.append(playerName);
        sb.append(", \"cardID\" : ");
        sb.append(cardID);
        sb.append("}");

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