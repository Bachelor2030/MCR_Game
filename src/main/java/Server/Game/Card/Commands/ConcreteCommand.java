package Server.Game.Card.Commands;

import Common.Receptors.Creature;
import Server.Game.ModelClasses.Command;

public abstract class ConcreteCommand implements Command {
    protected CommandName name;

    public ConcreteCommand(CommandName name){
        this.name = name;
    }

    @Override
    public CommandName getName() {
        return name;
    }
}
