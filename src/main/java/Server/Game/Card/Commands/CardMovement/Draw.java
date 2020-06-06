package Server.Game.Card.Commands.CardMovement;

import Server.Game.Card.Commands.CommandName;
import Common.Receptors.Player;

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
