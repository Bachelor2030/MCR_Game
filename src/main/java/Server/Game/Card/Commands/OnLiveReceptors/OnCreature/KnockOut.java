package Server.Game.Card.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.Card.Commands.CommandName;

public class KnockOut extends OnCreature {
    public KnockOut() {
        super(CommandName.KNOCK_OUT);
    }

    @Override
    public void execute() {
        if (receptors != null && receptors[0] != null)
            ((Creature)receptors[0]).knockOut();
    }

    @Override
    public void undo() {
        if (receptors != null && receptors[0] != null)
            ((Creature)receptors[0]).wakeUp();
    }
}
