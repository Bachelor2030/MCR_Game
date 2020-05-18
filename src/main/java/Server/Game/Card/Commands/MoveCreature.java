package Server.Game.Card.Commands;

import Common.Receptors.Creature;

public class MoveCreature extends ConcreteCommand {
    private Creature creature;

    public MoveCreature() {
        super(CommandName.MOVE_CREATURE);
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
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
