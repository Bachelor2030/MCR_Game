package Card;

import Command.Macro;
import ModelClasses.Invocator;

public abstract class Card implements Invocator {
    private Macro command;

    public Card() {}

    public void play() {
        command.execute();
    }

    @Override
    public void setCommand(Macro command) {
        this.command = command;
    }
}
