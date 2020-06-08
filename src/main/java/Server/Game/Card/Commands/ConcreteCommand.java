package Server.Game.Card.Commands;

import Server.Game.ModelClasses.Command;

public abstract class ConcreteCommand implements Command {
    protected CommandName name;

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
