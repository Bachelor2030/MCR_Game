package Card;

import Command.Macro;
import ModelClasses.Invocator;

public abstract class Card implements Invocator {
    private Macro command;
    private String name;

    public Card(String name) {
        this.name = name;
    }

    public void play() {
        command.execute();
    }

    @Override
    public void setCommand(Macro command) {
        this.command = command;
    }
}
