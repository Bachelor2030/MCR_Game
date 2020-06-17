package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;

public class Abandon extends PlayersAction {
  public Abandon() {
    super(CommandName.ABANDON);
  }

  @Override
  public void execute(Player player) {
    player.abandon();
  }

  @Override
  public void undo(Player player) {
    player.undoAbandon();
  }
}
