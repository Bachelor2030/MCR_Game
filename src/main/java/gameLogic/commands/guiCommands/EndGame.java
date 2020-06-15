package gameLogic.commands.guiCommands;

import gui.GameBoard;
import gameLogic.commands.CommandName;

public class EndGame extends GuiCommand {
    private char playerState;

    public EndGame() {
        super(CommandName.END_GAME);
    }

    public void setPlayerState(char playerState) {
        this.playerState = playerState;
    }

    @Override
    public void execute(GameBoard gameBoard) {
        gameBoard.exitGame();
    }

    @Override
    public void undo(GameBoard gameBoard) {}
}