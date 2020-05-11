package ModelClasses.Commands.CardMovement;

import Card.Card;
import ModelClasses.Commands.CommandName;
import ModelClasses.Receptors.Player;

public class Discard extends CardMovement {
    public Discard(Player player, Card card) {
        super(player, card, CommandName.DISCARD);
    }

    @Override
    public void execute() {
        player.discardCard(card);
    }

    @Override
    public void undo() {
        player.giveCard(card);
    }
}
