package Server.Game.ModelClasses.Commands.CardMovement;

import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Server.Game.ModelClasses.Commands.CommandName;

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
