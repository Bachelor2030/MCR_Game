package Client.GuiCommands;

import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.Position;

public class Place extends GuiCommand {
    private Position position;

    public Place() {
        super(CommandName.PLACE);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\"" + name + "\", \"player\" : " + playerName);

        sb.append(", \"position\" : { \"line\" : " +
                position.getLine() +
                ", \"spot\" : " +
                position.getPosition() +
                "}}");

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