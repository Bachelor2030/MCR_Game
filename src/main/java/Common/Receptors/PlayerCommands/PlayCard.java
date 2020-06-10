package Common.Receptors.PlayerCommands;

import Common.Receptors.PlayersAction;
import Server.Game.Card.Card;
import Server.Game.Card.Commands.CommandName;

public class PlayCard extends PlayersAction {
    private Card cardToPlay;

    public PlayCard() {
        super(CommandName.PLAY_CARD);
    }

    public void setCardToPlay(Card cardToPlay) {
        this.cardToPlay = cardToPlay;
    }

    @Override
    public void execute() {
        player.playCard(cardToPlay);
    }

    @Override
    public void undo() {
        player.undoCard(cardToPlay);
    }
}
