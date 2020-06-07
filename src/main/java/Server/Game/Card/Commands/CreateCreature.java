package Server.Game.Card.Commands;

import Server.Game.Card.Card;
import Server.Game.Position;
import Common.Receptors.Creature;

public class CreateCreature extends ConcreteCommand {
    private Position position;
    private Creature creature;

    public CreateCreature(){
        super(CommandName.CREATE_CREATURE);
    }

    public CreateCreature(Creature creature) {
        super(CommandName.CREATE_CREATURE);
        this.creature = creature;
    }

    public CreateCreature(Creature creature, Position position) {
        super(CommandName.CREATE_CREATURE);
        this.creature = creature;
        this.position = position;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
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