package gameLogic.Commands.OnLiveReceptors.OnCreature;

import gameLogic.Receptors.Creature;
import gameLogic.Commands.CommandName;

public class Retreat extends MoveCreature {
    public Retreat() {
        super(CommandName.RETREAT_CREATURE);
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).retreat(((Creature)receptors[i]).getSteps());
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).advance();
    }
}
