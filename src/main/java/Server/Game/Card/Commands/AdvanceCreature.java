package Server.Game.Card.Commands;

import Common.Receptors.Creature;

public class AdvanceCreature extends ConcreteCommand {
    private Creature creature;

    public AdvanceCreature(Creature creature) {
        super(CommandName.ADVANCE_CREATURE);
        this.creature = creature;
    }

    public AdvanceCreature() {
        super(CommandName.ADVANCE_CREATURE);
    }

    @Override
    public void execute() {
        creature.advance();
    }

    @Override
    public void undo() {
        creature.retreat(creature.getSteps());
    }
}
