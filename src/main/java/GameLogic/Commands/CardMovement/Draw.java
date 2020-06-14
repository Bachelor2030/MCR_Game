package GameLogic.Commands.CardMovement;

import GameLogic.Commands.CommandName;
import GameLogic.Receptors.Player;

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
