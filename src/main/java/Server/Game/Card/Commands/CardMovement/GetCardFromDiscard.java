package Server.Game.Card.Commands.CardMovement;

import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Server.Game.Card.Commands.CommandName;
import Common.Receptors.Player;

import java.util.HashMap;
import java.util.List;

public class GetCardFromDiscard extends CardMovement {
    private CardType type;

    public GetCardFromDiscard() {
        super(CommandName.DRAW_TYPE_FROM_DISCARD);
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
