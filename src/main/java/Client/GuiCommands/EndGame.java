package Client.GuiCommands;

import Client.View.GameBoard;
import Server.Game.ModelClasses.Commands.CommandName;

public class EndGame extends GuiCommand {
    private char playerState;

    public EndGame() {
        super(CommandName.END_GAME);
    }

    public void setPlayerState(char playerState) {
        this.playerState = playerState;
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\" : \"");
        sb.append(name);
        sb.append("\", \"player\" : \"");
        sb.append(playerName);
        sb.append("\", \"playerState\" : '");
        sb.append(playerState);
        sb.append("'}");

        return sb.toString();
    }

    @Override
    public void execute(GameBoard gameBoard) {
        gameBoard.exitGame();
    }

    @Override
    public void undo(GameBoard gameBoard) {}
}