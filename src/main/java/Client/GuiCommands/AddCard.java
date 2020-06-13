package Client.GuiCommands;

import Server.Game.ModelClasses.Commands.CommandName;

public class AddCard extends GuiCommand {
    private int cardID;

    public AddCard() {
        super(CommandName.ADD_CARD);
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
    public void execute() {
        // Todo : execution on the GUI
    }

    @Override
    public void undo() {
        // Todo : undo on the GUI
    }
}