package ModelClasses.Commands.CardMovement;

import ModelClasses.Commands.CommandName;
import ModelClasses.Receptors.Player;

public class Draw extends CardMovement {
    public Draw(Player player) {
        super(player, CommandName.DRAW);
    }

    @Override
    public void execute() {
        card = player.drawCard();
        setCard(card);
    }

    @Override
    public void undo() {
        player.removeFromHand(card);
        player.addToTopDeck(card);
    }
}
