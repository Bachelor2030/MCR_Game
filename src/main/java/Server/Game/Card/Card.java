package Server.Game.Card;

import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.Macro;
import Server.Game.ModelClasses.Invocator;

import java.util.ArrayList;
import java.util.Arrays;

public class Card implements Invocator {
    private final int ID;
    private Macro command;
    private String name;
    private int cost;
    private CardType type;
    private CommandName[] commandNames;

    public Card(int id, String name, CardType type, int cost, CommandName[] commandNames) {
        this.type = type;
        this.name = (type != null ? type.toString() + " " : "") + name;
        this.cost = cost;
        this.commandNames = commandNames;
        this.ID = id;
    }

    public Card(int id, String name, CardType type, int cost) {
        this.type = type;
        this.name = (type != null ? type.toString() + " " : "") + name;
        this.cost = cost;
        this.ID = id;
    }

    public void play() {
        if(command != null) {
            command.execute();
        }
    }

    public Macro getCommand() {
        return command;
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

    @Override
    public String toString() {
        return "id : " + ID + "\nname : " + name + "\ncost : " + cost + "\ncommands : " + Arrays.toString(commandNames) + "\n";
    }
}
