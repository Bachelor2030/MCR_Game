package Client.GuiCommands;

import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.Position;

public class ChangePoints extends GuiCommand {
    private Position position;
    private int newPointValue;
    private char pointsType;

    public ChangePoints() {
        super(CommandName.CHANGE_POINTS);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPointsType(char pointsType) {
        this.pointsType = pointsType;
    }

    public void setNewPointValue(int newPointValue) {
        this.newPointValue = newPointValue;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\"");
        sb.append(name);
        sb.append(" (");
        sb.append(pointsType);
        sb.append(")\", \"player\" : ");
        sb.append(playerName);

        sb.append(", \"effect\" : ");
        sb.append(newPointValue);

        sb.append(", \"position\" : { \"line\" : ");
        sb.append(position.getLine());
        sb.append(", \"spot\" : ");
        sb.append(position.getPosition());
        sb.append("}}");

        return sb.toString();
    }

    @Override
    public void execute() {
        // Todo : execution on the GUI
        switch (pointsType) {
            // Movement Points
            case 'M':

            // Attack Points
            case 'A':

            // Life Points
            case 'L':

            default: return;
        }
    }

    @Override
    public void undo() {
        // Todo : undo on the GUI
    }
}