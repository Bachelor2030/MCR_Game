package Common.Receptors.PlayerCommands;

import Common.Receptors.PlayersAction;
import Server.Game.Card.Commands.CommandName;

public class ChoosePosition extends PlayersAction {
    public ChoosePosition() {
        super(CommandName.CHOOSE_POSITION);
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
