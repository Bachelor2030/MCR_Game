package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;

public class KnockOut extends OnCreature {
    public KnockOut() {
        super(CommandName.KNOCK_OUT);
    }

    @Override
    public void execute(Creature creature) {
        creature.knockOut();
    }

    @Override
    public void undo(Creature creature) {
        creature.wakeUp();
    }
}
