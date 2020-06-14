package gameLogic.commands.onLiveReceptors.onCreature;

import gameLogic.receptors.Creature;
import gameLogic.commands.CommandName;

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
