package gameLogic.Commands.PlayersAction;

import gameLogic.Invocator.Card.Card;
import gameLogic.Commands.CommandName;

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
