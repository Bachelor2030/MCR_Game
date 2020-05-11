package ModelClasses.Commands;

import Game.GameBoard.Spot;
import ModelClasses.Receptors.Creature.Creature;

public class CreateCreature extends ConcreteCommand {
    private Spot position;
    private Creature creature;

    public CreateCreature(Creature creature, Spot position) {
        super(CommandName.CREATE_CREATURE);
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
