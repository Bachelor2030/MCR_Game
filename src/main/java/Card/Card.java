package Card;

import Command.Macro;
import ModelClasses.Invocator;
import Player.Creature.Creature;

public class Card implements Invocator {
    private Macro command;
    private String name;
    private int cost;
    private CardType type;

    public Card(String name, CardType type, int cost) {
        this.type = type;
        this.name = type.toString() + " " + name;
        this.cost = cost;
    }

    public void play() {
        command.execute();
    }

    @Override
    public void setCommand(Macro command) {
        this.command = command;
    }

    public int getCost() {
        return cost;
    }
}
