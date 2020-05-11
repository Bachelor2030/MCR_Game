package ModelClasses.Commands;

import ModelClasses.Command;

public abstract class ConcreteCommand implements Command {
    protected CommandName name;

    public ConcreteCommand(CommandName name){
        this.name = name;
    }
}
