package gameLogic.commands.cardMovement;

import gameLogic.commands.CommandName;
import gameLogic.invocator.card.Card;
import gameLogic.invocator.card.CardType;
import gameLogic.receptors.Player;
import network.states.ServerSharedState;

import java.util.HashMap;
import java.util.List;

public class DrawTypeFromDiscard extends CardMovement {
  private CardType type;

  public DrawTypeFromDiscard() {
    super(CommandName.DRAW_TYPE_FROM_DISCARD);
  }

  public void setType(CardType type) {
    this.type = type;
  }

  @Override
  public void execute(Player player) {
    HashMap<Integer, List<Card>> discard = player.getDiscard();
    for (int i = 1; i <= discard.size(); ++i) {
      for (Card c : discard.get(i)) {
        if (c.getType() == type) {
          card = c;
          break;
        }
      }
    }
    if (card != null) {
      player.giveCard(card);
    }
  }

  @Override
  public void undo(Player player) {
    if (card != null) {
      player.discardCard(card);
    }
  }
}
