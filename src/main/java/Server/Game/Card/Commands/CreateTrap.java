package Server.Game.Card.Commands;

import Server.Game.Position;
import Common.Receptors.Trap;

public class CreateTrap extends ConcreteCommand {
    private Position position;
    private Trap trap;

    public CreateTrap(Trap trap){
        super(CommandName.CREATE_TRAP);
        this.trap = trap;
    }

    public CreateTrap() {
        super(CommandName.CREATE_TRAP);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
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
