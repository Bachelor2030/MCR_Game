package ModelClasses.Commands;

import ModelClasses.Command;
import ModelClasses.Receptors.Creature.Creature;

public class MoveCreature implements Command {
    private Creature creature;

    public MoveCreature(Creature creature) {
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
