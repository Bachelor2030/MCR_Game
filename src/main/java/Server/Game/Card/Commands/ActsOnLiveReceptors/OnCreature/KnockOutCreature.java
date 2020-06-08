package Server.Game.Card.Commands.ActsOnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.Card.Commands.CommandName;

public class KnockOutCreature extends ActOnCreature {
    public KnockOutCreature() {
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
