package GameLogic.Commands.CardMovement;

import GameLogic.Commands.CommandName;
import GameLogic.Receptors.Player;

public class Discard extends CardMovement {
    public Discard() {
        super(CommandName.DISCARD);
    }

    @Override
    public void execute(Player player) {
        player.discardCard(card);
    }

    @Override
    public void undo(Player player) {
        player.giveCard(card);
    }

}
