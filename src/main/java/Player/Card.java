package Player;

import Command.Macro;
import ModelClasses.Invocator;

public class Card implements Invocator {
    private CardType type;
    private Macro command;

    public Card(CardType type) {
        this.type = type;
    }

    public void play() {
        command.execute();
    }

    @Override
    public void setCommand(Macro command) {
        this.command = command;
    }
}
