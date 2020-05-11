package ModelClasses.Commands;

import ModelClasses.Command;
import Game.GameBoard.Spot;
import ModelClasses.Receptors.Creature.Creature;

public class CreateCreature implements Command {
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
