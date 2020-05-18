package Server.Game.Card.Commands;

import Server.Game.Position;
import Common.Receptors.Trap;

public class CreateTrap extends ConcreteCommand {
    private Position position;
    private Trap trap;

    public CreateTrap() {
        super(CommandName.CREATE_TRAP);
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void execute() {
        trap.setPosition(position);
    }

    @Override
    public void undo() {
        trap.setPosition(null);
    }
}
