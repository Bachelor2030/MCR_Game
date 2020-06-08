package Server.Game.Utils;

import Common.Receptors.Creature;
import Common.Receptors.Trap;
import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Server.Game.Card.Commands.*;
import Server.Game.Card.Commands.ActsOnLiveReceptors.OnCreature.CreateCreature;
import Server.Game.Game;
import Server.Game.ModelClasses.Macro;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class ParserLauncher {
    public static void main(String[] args) {
        String file = "src/main/resources/game.json";

        Game game = parseJsonGame(file);
        game.startGame();
    }

    private static Game parseJsonGame(String file) {
        Game game = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader( new InputStreamReader(fileInputStream));

            StringBuilder sb = new StringBuilder();
            String line;
            while(( line = br.readLine()) != null ) {
                sb.append( line );
                sb.append( '\n' );
            }

            JsonUtil jsonUtil = new JsonUtil();
            ArrayList<Card> allCards = parseJsonCards(jsonUtil.getJsonContent("src/main/resources/cards.json"));

            System.out.println("Read " + file);
            GameJsonParser gameJsonParser = new GameJsonParser(allCards, "src/main/resources/");
            game = gameJsonParser.parseJson(sb.toString());

            fileInputStream.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return game;
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

            JSONArray commandsJSON = card.getJSONArray("card commands");
            for (int index = 0; index < commandsJSON.length(); ++index) {
                concreteCommands.add(CommandName.getCommandName(commandsJSON.getString(index)).getCommand());
            }

            Card c = new Card(id, cardName, cardType, cardCost);
            c.setCommand(new Macro(concreteCommands));
            cards.add(c);
        }

        return cards;
    }
}
