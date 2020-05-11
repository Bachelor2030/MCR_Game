package ModelClasses.Commands;

import Game.GameBoard.Spot;
import ModelClasses.Receptors.Trap;

public class CreateTrap extends ConcreteCommand {
    private Spot position;
    private Trap trap;

    public CreateTrap(Trap trap, Spot position){
        super(CommandName.CREATE_TRAP);
        this.trap = trap;
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
