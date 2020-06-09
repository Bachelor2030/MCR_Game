package Common.Receptors.PlayerCommands;

import Common.Receptors.PlayersAction;
import Server.Game.Card.Commands.CommandName;

public class ChooseCreature extends PlayersAction {
    public ChooseCreature() {
        super(CommandName.CHOOSE_CREATURE);
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
