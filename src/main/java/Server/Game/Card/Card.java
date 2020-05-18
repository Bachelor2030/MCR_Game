package Server.Game.Card;

import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.Command;
import Server.Game.ModelClasses.Macro;
import Server.Game.ModelClasses.Invocator;

import java.util.Arrays;

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

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public CardType getType() {
        return type;
    }

    public CommandName[] getCommandNames() {
        return commandNames;
    }

    public void play() {
        command.execute();
    }

    @Override
    public void setCommand(Macro command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Card{" +
                "command=" + command +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", type=" + type +
                ", commandNames=" + Arrays.toString(commandNames) +
                '}';
    }
}
