package gameLogic.commands.guiCommands;

import gameLogic.commands.CommandName;
import gui.GameBoard;

public class EndPlayerTurn extends GuiCommand {
  public EndPlayerTurn() {
    super(CommandName.END_PLAYER_TURN);
  }

  @Override
  public void execute(GameBoard gameBoard) {}

  @Override
  public void undo(GameBoard gameBoard) {}
}
