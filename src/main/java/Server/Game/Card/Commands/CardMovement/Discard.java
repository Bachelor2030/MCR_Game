package Server.Game.Card.Commands.CardMovement;

import Server.Game.Card.Card;
import Server.Game.Card.Commands.CommandName;
import Common.Receptors.Player;

public class Discard extends CardMovement {
    public Discard(Player player, Card card) {
        super(player, card, CommandName.DISCARD);
    }

    public Discard() {
        super(CommandName.DISCARD);
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
