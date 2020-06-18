package gameLogic.commands.playersAction;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;
import network.states.ServerSharedState;

public class EndTurn extends PlayersAction {
  public EndTurn() {
    super(CommandName.END_TURN);
  }

  @Override
  public void execute(Player player, ServerSharedState serverSharedState) {
    player.endTurn(serverSharedState);
  }

  @Override
  public void undo(Player player, ServerSharedState serverSharedState) {
    player.continueTurn();
  }
}
