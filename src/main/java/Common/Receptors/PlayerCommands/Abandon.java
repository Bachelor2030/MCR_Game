package Common.Receptors.PlayerCommands;

import Common.Receptors.PlayersAction;
import Server.Game.Card.Commands.CommandName;

public class Abandon extends PlayersAction {
    Abandon() {
        super(CommandName.ABANDON);
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
