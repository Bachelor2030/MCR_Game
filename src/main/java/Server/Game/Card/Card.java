package Server.Game.Card;

import Server.Game.Card.Commands.CommandName;
import Server.Game.Card.Commands.HitLiveReceptor;
import Server.Game.ModelClasses.Command;
import Server.Game.ModelClasses.Macro;
import Server.Game.ModelClasses.Invocator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Modelizes a card for the game
 */
public class Card implements Invocator {
    private final int ID;                   // the id of the card
    private Macro command;                  // the command that it executes
    private String name;                    // the name of the card
    private int cost;                       // the cost (in action points)
    private CardType type;                  // the type of the card

    /**
     * Creates a card with the given information
     * @param id the id of the card to be created
     * @param name  the name of the card
     * @param type  the type of the card
     * @param cost  it's cost (int action points)
     */
    public Card(int id, String name, CardType type, int cost) {
        this.type = type;
        this.name = (type != null ? type.toString() + " " : "") + name;
        this.cost = cost;
        this.ID = id;
    }

    /**
     * Plays the card which executes its commands
     */
    public void play() {
        if(command != null) {
            command.execute();
        }
    }

    /**
     * Get the commands of the card
     * @return the cards macro
     */
    public Macro getCommand() {
        return command;
    }

    /**
     * Get the cost of the card
     * @return cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Get the name of the card
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *  Get the type of card it is
     * @return type
     */
    public CardType getType() {
        return type;
    }

    /**
     * Get the id of the current card
     * @return id
     */
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "ID : " + ID + "\nName : " + name + "\nCost : " + cost + "\nCommand : " + command + "\n";
    }

    @Override
    public void setCommand(Macro command) {
        this.command = command;
    }
}
