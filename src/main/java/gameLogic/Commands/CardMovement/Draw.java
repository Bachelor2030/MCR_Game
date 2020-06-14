package gameLogic.Commands.CardMovement;

import gameLogic.Commands.CommandName;

public class Draw extends CardMovement {
    public Draw() {
        super(CommandName.DRAW);
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
