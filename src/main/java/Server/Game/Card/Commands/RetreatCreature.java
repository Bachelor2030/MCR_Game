package Server.Game.Card.Commands;

import Common.Receptors.Creature;

public class RetreatCreature extends ConcreteCommand {
    private Creature creature;

    public RetreatCreature() {
        super(CommandName.RETREAT_CREATURE);
    }

    @Override
    public void execute() {
        if(creature != null) {
            creature.retreat(creature.getSteps());
        }
    }

    @Override
    public void undo() {
        if(creature != null) {
            creature.advance();
        }
    }
}
