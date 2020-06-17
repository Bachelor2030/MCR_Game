package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;

public class EndTurn extends PlayersAction {
  public EndTurn() {
    super(CommandName.END_TURN);
  }

  @Override
  public void execute(Player player) {
    player.endTurn();
  }

  @Override
  public void undo(Player player) {
    player.continueTurn();
  }
}
