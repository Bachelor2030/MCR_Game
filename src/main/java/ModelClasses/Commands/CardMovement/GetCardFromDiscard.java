package ModelClasses.Commands.CardMovement;

import Card.Card;
import Card.CardType;
import ModelClasses.Commands.CommandName;
import ModelClasses.Receptors.Player;

import java.util.HashMap;
import java.util.List;

public class GetCardFromDiscard extends CardMovement {
    private CardType type;

    public GetCardFromDiscard(Player player, CardType type) {
        super(player, CommandName.DRAW_TYPE_FROM_DISCARD);
        this.type = type;
    }

    @Override
    public void execute() {
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
    public void undo() {
        if(card != null) {
            player.discardCard(card);
        }
    }
}
