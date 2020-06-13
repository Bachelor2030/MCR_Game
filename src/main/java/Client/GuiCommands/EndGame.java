package Client.GuiCommands;

import Server.Game.ModelClasses.Commands.CommandName;

public class EndGame extends GuiCommand {
    private char playerState;

    public EndGame() {
        super(CommandName.END_GAME);
    }

    public void setplayerState(char playerState) {
        this.playerState = playerState;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\"");
        sb.append(name);
        sb.append("\", \"player\" : ");
        sb.append(playerName);
        sb.append(", \"playerState\" : ");
        sb.append(playerState);
        sb.append("}");

        return sb.toString();
    }

    @Override
    public void execute() {
        // Todo : execution on the GUI
        switch (playerState) {
            // The player won
            case 'W':

            // The player lost
            case 'L':

            default: return;
        }
    }

    @Override
    public void undo() {
        // Todo : undo on the GUI
    }
}