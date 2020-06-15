package gameLogic.commands.guiCommands;

import gui.GameBoard;
import gameLogic.commands.CommandName;

public class EndPlayerTurn extends GuiCommand {
    public EndPlayerTurn() {
        super(CommandName.END_PLAYER_TURN);
    }

    @Override
    public void execute(GameBoard gameBoard) {

    }

    @Override
    public void undo(GameBoard gameBoard) {

    }
}
