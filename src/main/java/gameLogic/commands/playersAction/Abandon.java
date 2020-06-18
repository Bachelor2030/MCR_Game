package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;
import network.states.ServerSharedState;

public class Abandon extends PlayersAction {
  public Abandon() {
    super(CommandName.ABANDON);
  }

  @Override
  public void execute(Player player, ServerSharedState serverSharedState) {
    player.abandon();
  }

  @Override
  public void undo(Player player, ServerSharedState serverSharedState) {
    player.undoAbandon();
  }
}
