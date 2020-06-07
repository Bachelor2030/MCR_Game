package Server.Game.Card.Commands;

import Common.Receptors.Creature;

public class MoveCreature extends ConcreteCommand {
    private Creature creature;

    public MoveCreature(Creature creature) {
        super(CommandName.MOVE_CREATURE);
        this.creature = creature;
    }

    public MoveCreature() {
        super(CommandName.MOVE_CREATURE);
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
