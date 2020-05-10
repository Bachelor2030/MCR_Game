package Command;

import Player.Creature.Creature;

public class MoveCreature extends Macro {
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
