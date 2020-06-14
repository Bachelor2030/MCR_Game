package GameLogic.Commands.OnLiveReceptors.OnCreature;

import GameLogic.Receptors.Creature;
import GameLogic.Commands.CommandName;

public class Advance extends MoveCreature {
    public Advance() {
        super(CommandName.ADVANCE_CREATURE);
    }

    @Override
    public void execute(Creature creature) {
        from = creature.getPosition();
        creature.advance();
        from = creature.getPosition();
    }

    @Override
    public void undo(Creature creature) {
        from = creature.getPosition();
        creature.retreat(creature.getSteps());
        from = creature.getPosition();
    }
}
