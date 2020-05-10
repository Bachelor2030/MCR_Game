package Card;

import Command.Macro;
import ModelClasses.Invocator;
import Player.Creature.Creature;

public class Card implements Invocator {
    private Macro command;
    private String name;
    private CardType type;

    public Card(String name, CardType type) {
        this.type = type;
        this.name = type.toString() + " " + name;
    }

    public void play() {
        command.execute();
    }

    @Override
    public void setCommand(Macro command) {
        this.command = command;
    }
}
