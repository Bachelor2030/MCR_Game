package Command.Commands;

import Command.Command;
import GameBoard.Spot;
import Receptors.Creature.Creature;

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
