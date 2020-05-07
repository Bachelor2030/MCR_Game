package ModelClasses;


import Command.ConcreteCommand;

public interface Invocator {
    void setCommand(ConcreteCommand command);
}
