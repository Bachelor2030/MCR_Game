package Server.Game.ModelClasses.Commands.CardMovement;

import Server.Game.ModelClasses.Commands.CommandName;

public class Discard extends CardMovement {
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
