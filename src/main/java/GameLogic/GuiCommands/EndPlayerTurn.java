package GameLogic.GuiCommands;

import GUI.GameBoard;
import GameLogic.ModelClasses.Commands.CommandName;

public class EndPlayerTurn extends GuiCommand {
    public EndPlayerTurn() {
        super(CommandName.END_PLAYER_TURN);
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"type\" : \"GUI Command\", \"name\"");
        sb.append(name);
        sb.append("\", \"player\" : ");
        sb.append(playerName);
        sb.append("}");

        return sb.toString();
    }

    @Override
    public void execute(GameBoard gameBoard) {

    }

    @Override
    public void undo(GameBoard gameBoard) {

    }
}
