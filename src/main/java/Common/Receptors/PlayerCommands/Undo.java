package Common.Receptors.PlayerCommands;

import Common.Receptors.PlayersAction;
import Server.Game.Card.Card;
import Server.Game.Card.Commands.CommandName;

public class Undo extends PlayersAction {
    public Undo() {
        super(CommandName.UNDO);
    }

    @Override
    public void execute() {
        Card card = player.lastCardPlayed();
        if (card != null)
            player.undoCard(card);
    }

    @Override
    public void undo() {
        player.lastCardPlayed().play();
    }
}
