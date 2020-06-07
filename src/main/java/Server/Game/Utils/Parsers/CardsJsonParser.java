package Server.Game.Utils.Parsers;

import Common.Receptors.Creature;
import Common.Receptors.Trap;
import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Server.Game.Card.Commands.CommandName;
import Server.Game.ModelClasses.Command;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardsJsonParser {
    private static HashMap<Integer, Integer> indexQuantityList;

    public static ArrayList<Card> parseJson(String json, String path) throws JSONException {
        indexQuantityList = new HashMap<>();

        ArrayList<Card> allCards = parseJsonCards(getJsonContent(path + "cards.json"));
        ArrayList<Trap> traps = parseJsonTraps(getJsonContent(path + "traps.json"));
        ArrayList<Command> spells = parseJsonSpells(getJsonContent(path + "spells.json"));
        ArrayList<Creature> creatures = parseJsonCreatures(getJsonContent(path + "creatures.json"));

        ArrayList<Card> cards = new ArrayList<>();
        JSONObject obj = new JSONObject(getJsonContent(path + json));

        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println("Reading " + pageName);

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
            for (Card c : allCards) {
                if (c.getID() == key) {
                    for (int i = 0; i < quantity; i++) {
                        cards.add(c);
                    }
                }
            }
        }

        return cards;
    }

    private static ArrayList<Creature> parseJsonCreatures(String jsonContent) throws JSONException {
        // Todo
        ArrayList<Creature> creatures = new ArrayList<>();

        JSONObject obj = new JSONObject(jsonContent);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println("Reading " + pageName);

        JSONArray arr = obj.getJSONArray("creatures");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject creature = arr.getJSONObject(i);
            String name = creature.keys().next().toString();
            System.out.println("Creature --> " + name);

            int id = creature.getInt("id");
            CardType type = CardType.getType(creature.getString("type"));
            int cost = creature.getInt("cost");

            JSONArray commandsJSON = creature.getJSONArray("commands");
            CommandName[] commandNames = new CommandName[commandsJSON.length()];
            for (int index = 0; index < commandsJSON.length(); ++index) {
                commandNames[index] = CommandName.getCommandName(commandsJSON.getString(index));
            }

            //creatures.add(new Card(id, name, type, cost));
        }

        return creatures;
    }

    private static ArrayList<Command> parseJsonSpells(String jsonContent) {
        // Todo
        /*
        ArrayList<Command> spells = new ArrayList<>();

        JSONObject obj = new JSONObject(jsonContent);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println("Reading " + pageName);

        JSONArray arr = obj.getJSONArray("cards");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject card = arr.getJSONObject(i);

            int id = card.getInt("id");
            String name = card.getString("name");
            CardType type = CardType.getType(card.getString("type"));
            int cost = card.getInt("cost");

            JSONArray commandsJSON = card.getJSONArray("commands");
            CommandName[] commandNames = new CommandName[commandsJSON.length()];
            for (int index = 0; index < commandsJSON.length(); ++index) {
                commandNames[index] = CommandName.getCommandName(commandsJSON.getString(index));
            }

            spells.add(new Card(id, name, type, cost));
        }

        return spells;

         */
        return null;
    }

    private static ArrayList<Trap> parseJsonTraps(String jsonContent) {
        // Todo
        /*
        ArrayList<Trap> traps = new ArrayList<>();

        JSONObject obj = new JSONObject(jsonContent);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println("Reading " + pageName);

        JSONArray arr = obj.getJSONArray("cards");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject card = arr.getJSONObject(i);

            int id = card.getInt("id");
            String name = card.getString("name");
            CardType type = CardType.getType(card.getString("type"));
            int cost = card.getInt("cost");

            JSONArray commandsJSON = card.getJSONArray("commands");
            CommandName[] commandNames = new CommandName[commandsJSON.length()];
            for (int index = 0; index < commandsJSON.length(); ++index) {
                commandNames[index] = CommandName.getCommandName(commandsJSON.getString(index));
            }

            traps.add(new Card(id, name, type, cost));
        }

        return traps;

         */
        return null;
    }

    private static ArrayList<Card> parseJsonCards(String json) throws JSONException {
        ArrayList<Card> cards = new ArrayList<>();

        JSONObject obj = new JSONObject(json);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println("Reading " + pageName);

        JSONArray arr = obj.getJSONArray("cards");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject card = arr.getJSONObject(i);

            int id = card.getInt("id");
            String name = card.getString("name");
            CardType type = CardType.getType(card.getString("type"));
            int cost = card.getInt("cost");

            JSONArray commandsJSON = card.getJSONArray("commands");
            CommandName[] commandNames = new CommandName[commandsJSON.length()];
            for (int index = 0; index < commandsJSON.length(); ++index) {
                commandNames[index] = CommandName.getCommandName(commandsJSON.getString(index));
            }

            cards.add(new Card(id, name, type, cost));
        }

        return cards;
    }

    private static String getJsonContent(String file) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
