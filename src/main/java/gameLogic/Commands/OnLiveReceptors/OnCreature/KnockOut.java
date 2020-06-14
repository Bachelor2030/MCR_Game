package gameLogic.Commands.OnLiveReceptors.OnCreature;

import gameLogic.Receptors.Creature;
import gameLogic.Commands.CommandName;

public class KnockOut extends OnCreature {
    public KnockOut() {
        super(CommandName.KNOCK_OUT);
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).knockOut();
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).wakeUp();
    }
}
