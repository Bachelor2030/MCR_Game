package Server.Game.Card;

import Common.Receptors.Creature;
import Common.Receptors.Trap;
import Server.Game.Card.Commands.*;
import Server.Game.ModelClasses.Macro;
import Server.Game.ModelClasses.Invocator;
import Server.Game.Utils.JsonUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Modelizes a card for the game
 */
public class Card implements Invocator {
    private static final JsonUtil jsonUtil = new JsonUtil();
    private static ArrayList<Card> all;
    static {
        try {
            all = parseJsonCards(jsonUtil.getJsonContent("src/main/resources/cards.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
            System.out.println("Playing : " + this);
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

    /**************************
     *  JSON utils for Cards  *
     **************************/

    public static ArrayList<Card> parseJson(String file) {
        ArrayList<Card> cards = new ArrayList<>();

        try {
            HashMap<Integer, Integer> indexQuantityList = new HashMap<>();

            JSONObject obj = new JSONObject(jsonUtil.getJsonContent(file));

            JSONArray arr = obj.getJSONArray("cards");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject card = arr.getJSONObject(i);

                int id = card.getInt("id");
                int cost = card.getInt("quantity");

                indexQuantityList.put(id, cost);

            }

            for(Map.Entry<Integer, Integer> entry : indexQuantityList.entrySet()) {
                int key = entry.getKey();
                int quantity = entry.getValue();
                for (Card c : all) {
                    if (c.getID() == key) {
                        for (int i = 0; i < quantity; i++) {
                            cards.add(c);
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cards;
    }

    private static ArrayList<Card> parseJsonCards(String json) throws JSONException {
        ArrayList<Card> cards = new ArrayList<>();

        JSONObject obj = new JSONObject(json);

        JSONArray arr = obj.getJSONArray("cards");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject card = arr.getJSONObject(i);

            int id = card.getInt("id");
            String cardName = card.getString("name");
            CardType cardType = CardType.getType(card.getString("type"));
            int cardCost = card.getInt("cost");

            JSONArray commandsJSON = card.getJSONArray("commands");
            ArrayList<ConcreteCommand> concreteCommands = new ArrayList<>();

            if (cardType == CardType.CREATURE) {
                CreateCreature createCreature = new CreateCreature();
                JSONObject jsonCreature = card.getJSONObject("creature");
                Creature creature = new Creature(
                                jsonCreature.getString("name"),
                                jsonCreature.getInt("life"),
                                jsonCreature.getInt("steps"),
                                jsonCreature.getInt("attack"));
                createCreature.setCreature(creature);
                concreteCommands.add(createCreature);
            }
            else if (cardType == CardType.TRAP) {
                JSONObject jsonTrap = card.getJSONObject("trap");
                ArrayList<ConcreteCommand> trapCommands = new ArrayList<>();
                JSONArray cmds = jsonTrap.getJSONArray("commands");

                for (int j = 0; j < cmds.length(); j++) {
                    trapCommands.add(CommandName.getCommandName(cmds.getString(j)).getCommand());
                }

                Trap trap = new Trap(jsonTrap.getString("name"), new Macro(trapCommands));

                concreteCommands.add(new CreateTrap(trap));
            }

            for (int index = 0; index < commandsJSON.length(); ++index) {
                String cmdType = commandsJSON.getString(index);
                CommandName cmdName = CommandName.getCommandName(cmdType);
                ConcreteCommand cmd = cmdName.getCommand();
                concreteCommands.add(cmd);
            }

            Card c = new Card(id, cardName, cardType, cardCost);
            c.setCommand(new Macro(concreteCommands));
            cards.add(c);
        }

        return cards;
    }
}
