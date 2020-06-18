package gameLogic.commands.cardMovement;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;
import network.states.ServerSharedState;

public class Draw extends CardMovement {
  public Draw() {
    super(CommandName.DRAW);
  }

  @Override
  public void execute(Player player) {
    card = player.drawCard();
    setCard(card);
  }

  @Override
  public void undo(Player player) {
    player.removeFromHand(card);
    player.addToTopDeck(card);
  }
}
