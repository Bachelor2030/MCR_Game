package Command;

import GameBoard.Spot;
import Player.Creature.Creature;

public class CreateCreature extends Macro {
    private Spot position;
    private Creature creature;

    public CreateCreature(Creature creature, Spot position) {
        this.creature = creature;
        this.position = position;
    }

    @Override
    public void execute() {
        creature.place(position);
    }

    @Override
    public void undo() {
        creature.place(null);
    }
}
