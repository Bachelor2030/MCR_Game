package Common.Receptors.PlayerCommands;

import Common.Receptors.PlayersAction;
import Server.Game.Card.Commands.CommandName;

public class PlayCard extends PlayersAction {
    public PlayCard() {
        super(CommandName.PLAY_CARD);
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
