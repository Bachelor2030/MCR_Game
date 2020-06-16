package gameLogic.invocator.card;

import gameLogic.commands.Macro;
import gameLogic.invocator.Invocator;
import network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Modelizes a card for the game
 */
public class Card implements Invocator {
    protected final int ID;                   // the id of the card
    protected Macro command;                  // the command that it executes
    protected String name;                    // the name of the card
    protected int cost;                       // the cost (in action points)
    protected CardType type;                  // the type of the card

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

    public void undo() {
        if(command != null) {
            command.undo(command.getReceptor());
        }
    }

    /**
     * Plays the card which executes its commands
     */
    public void play() {
        if(command != null) {
            System.out.println("Playing : " + this);
            command.execute(command.getReceptor());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return ID == card.ID &&
                cost == card.cost &&
                Objects.equals(command, card.command) &&
                Objects.equals(name, card.name) &&
                type == card.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, command, name, cost, type);
    }

    public JSONObject toJSON() {
        JSONObject c = new JSONObject();
        try {
            c.put(Messages.JSON_TYPE_CARD_ID, ID);
            c.put(Messages.JSON_TYPE_NAME, name);
            c.put(Messages.JSON_TYPE, type);
            c.put(Messages.JSON_TYPE_COST, cost);
            c.put(Messages.JSON_TYPE_COMMANDS, command.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return c;
    }
}
