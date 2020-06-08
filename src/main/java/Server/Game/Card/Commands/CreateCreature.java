package Server.Game.Card.Commands;

import Server.Game.Position;
import Common.Receptors.Creature;

public class CreateCreature extends ConcreteCommand {
    private Position position;
    private Creature creature;

    public CreateCreature(){
        super(CommandName.CREATE_CREATURE);
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    public Creature getCreature() {
       return creature;
    }

    public void setPosition(Position position) {
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