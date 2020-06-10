package Server.Game.ModelClasses;

import Server.Game.Card.Commands.CommandName;

public abstract class ConcreteCommand implements Command {
    protected final CommandName name;

    public ConcreteCommand(CommandName name){
        this.name = name;
    }

    public CommandName getName() {
        return name;
    }

    @Override
    public String toString() {
        if (name != null)
            return name.toString();
        return "";
    }
}
