package ModelClasses.Commands;

import ModelClasses.Receptors.Creature.Creature;

public class MoveCreature extends ConcreteCommand {
    private Creature creature;

    public MoveCreature(Creature creature) {
        super(CommandName.MOVE_CREATURE);
        this.creature = creature;
    }

    @Override
    public void execute() {
        creature.advance();
    }

    @Override
    public void undo() {
        creature.retreat();
    }
}
