package Server.Game.Card.Commands;

import Common.Receptors.Creature;

public class KnockOutCreature extends ConcreteCommand {
    private Creature creature;

    public KnockOutCreature() {
        super(CommandName.KNOCK_OUT);
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    @Override
    public void execute() {
        if (creature != null)
            creature.knockOut();
    }

    @Override
    public void undo() {
        if (creature != null)
            creature.wakeUp();
    }
}
