package GameLogic.ModelClasses.Commands.PlayersAction;

import GameLogic.Card.Card;
import GameLogic.ModelClasses.Commands.CommandName;

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
