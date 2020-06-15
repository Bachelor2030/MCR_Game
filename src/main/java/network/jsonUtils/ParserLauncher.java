package network.jsonUtils;

import gameLogic.receptors.Creature;
import gameLogic.receptors.Trap;
import gameLogic.invocator.card.Card;
import gameLogic.invocator.card.CardType;
import gameLogic.commands.CommandName;
import gameLogic.commands.CreateTrap;
import gameLogic.commands.onLiveReceptors.onCreature.Create;
import gameLogic.Game;
import gameLogic.commands.ConcreteCommand;
import gameLogic.commands.Macro;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class ParserLauncher {
    public static void main(String[] args) {
        String file = "src/main/resources/json/game.json";

        Game game = parseJsonGame(file, 5, 10);

        /*
        ArrayList<ConcreteCommand> commands = new ArrayList<>();
        Create create = new Create();

        Creature pier = new Creature("pier", 1, 1, 1), seb = new Creature("Sebas-chan", 2, 2, 2);
        pier.setOwner(new Player("Clarusso", null));
        seb.setOwner(new Player("Clarusso", null));
        pier.setOriginCard(new card(1, "Pier", CardType.CREATURE, 12));
        seb.setOriginCard(new card(2, "Seb", CardType.CREATURE, 13));

        create.setCreatures(new Creature[]{pier, seb});
        create.setPositions(new Position[]{new Position(new GUILine(1), 1), new Position(new GUILine(2), 2)});
        create.execute();
        commands.add(create);

        KnockOut ko = new KnockOut();
        ko.setReceptors(new LiveReceptor[]{pier, seb});
        ko.execute();
        commands.add(ko);

        Advance advance = new Advance();
        advance.setCreatures(new Creature[]{pier});
        advance.execute();
        commands.add(advance);

        Macro m = new Macro(commands);
        for (int i = 0; i < m.toJson().length; i++) {
            System.out.println("Command n°" + i);
            System.out.println(m.toJson()[i]);
        }*/
    }

    public static Game parseJsonGame(String file, int nbr_lines, int nbr_spots) {
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
            ArrayList<Card> allCards = parseJsonCards(jsonUtil.getJsonContent("src/main/resources/json/cards.json"));

            System.out.println("Read " + file);
            GameJsonParser gameJsonParser = new GameJsonParser(allCards, "src/main/resources/json/", nbr_lines, nbr_spots);
            game = gameJsonParser.parseJson(sb.toString());

            fileInputStream.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return game;
    }

    public static ArrayList<Card> parseJsonCards(String json) throws JSONException {
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
                Create create = new Create();
                JSONObject jsonCreature = card.getJSONObject("creature");
                Creature creature = new Creature(
                        jsonCreature.getString("name"),
                        jsonCreature.getInt("life"),
                        jsonCreature.getInt("steps"),
                        jsonCreature.getInt("attack"));
                create.setCreature(creature);
                concreteCommands.add(create);
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

            for (Create concreteCommand : c.getCommand().getCreateCreature()) {
                if (concreteCommand == null)
                    break;
                concreteCommand.getCreature().setOriginCard(c);
            }
            cards.add(c);
        }

        return cards;
    }
}
