package GameLogic.Commands.CardMovement;

import GameLogic.Invocator.Card.Card;
import GameLogic.Invocator.Card.CardType;
import GameLogic.Commands.CommandName;
import GameLogic.Receptors.Player;

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
            for (Card c: discard.get(i)) {
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
        if(card != null) {
            player.discardCard(card);
        }
    }
}
