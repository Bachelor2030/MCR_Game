package Server.Game.Card;

import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.Macro;
import Server.Game.ModelClasses.Invocator;

public class Card implements Invocator {
    private Macro command;
    private String name;
    private int cost;
    private CardType type;
    private CommandName[] commandNames;

    public Card(String name, CardType type, int cost, CommandName[] commandNames) {
        this.type = type;
        this.name = (type != null ? type.toString() + " " : "") + name;
        this.cost = cost;
        this.commandNames = commandNames;
    }

    public Card(String name, CardType type, int cost) {
        this.type = type;
        this.name = (type != null ? type.toString() + " " : "") + name;
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

    public String getName() {
        return name;
    }

    public CardType getType() {
        return type;
    }
}
