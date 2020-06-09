package Common.Receptors.PlayerCommands;

import Common.Receptors.PlayersAction;
import Server.Game.Card.Commands.CommandName;

public class ChooseCreature extends PlayersAction {
    public ChooseCreature() {
        super(CommandName.CHOOSE_CREATURE);
    }

    @Override
    public void execute() {
        // TODO : idée, choix de créature sur laquelle faire action --> set action et l'executer ici (?)
    }

    @Override
    public void undo() {

    }
}
