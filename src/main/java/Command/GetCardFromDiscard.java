package Command;

import Card.Card;
import Card.CardType;
import Receptors.Player;

import java.util.HashMap;
import java.util.List;

public class GetCardFromDiscard implements Command {
    private Player player;
    private CardType type;
    private Card cardAdded;

    public GetCardFromDiscard(Player player, CardType type) {
        this.player = player;
        this.type = type;
    }

    @Override
    public void execute() {
        HashMap<Integer, List<Card>> discard = player.getDiscard();
        for (int i = 1; i <= discard.size(); ++i) {
            for (Card card: discard.get(i)) {
                if (card.getType() == type) {
                    cardAdded = card;
                    break;
                }
            }
        }
        if (cardAdded != null) {
            player.giveCard(cardAdded);
        }
    }

    @Override
    public void undo() {
        if(cardAdded != null) {
            player.discardCard(cardAdded);
        }
    }
}
