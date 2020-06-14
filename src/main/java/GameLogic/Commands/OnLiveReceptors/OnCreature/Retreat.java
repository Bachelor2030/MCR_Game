package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;

public class Retreat extends MoveCreature {
    public Retreat() {
        super(CommandName.RETREAT_CREATURE);
    }

    @Override
    public void execute(Creature creature) {
        creature.retreat(creature.getSteps());
    }

    @Override
    public void undo(Creature creature) {
        creature.advance();
    }
}
