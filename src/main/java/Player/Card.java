package Player;

import Command.ConcreteCommand;
import ModelClasses.Invocator;

public class Card implements Invocator {
    private CardType type;
    private ConcreteCommand command;

    public Card(CardType type) {
        this.type = type;
    }

    public void play() {
        command.execute();
    }

    @Override
    public void setCommand(ConcreteCommand command) {
        this.command = command;
    }
}
