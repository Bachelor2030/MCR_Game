package Common.Network.JsonUtils;

import Common.GameBoard.Line;
import Common.Receptors.Creature;
import Common.Receptors.Player;
import Common.Receptors.Trap;
import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.Commands.CreateTrap;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature.Advance;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature.Create;
import Server.Game.Game;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature.KnockOut;
import Server.Game.ModelClasses.ConcreteCommand;
import Server.Game.ModelClasses.LiveReceptor;
import Server.Game.ModelClasses.Macro;
import Server.Game.Position;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class ParserLauncher {
    public static void main(String[] args) {
        String file = "src/main/resources/json/game.json";

        Game game = parseJsonGame(file);
        //game.startGame();
        ArrayList<ConcreteCommand> commands = new ArrayList<>();
        Create create = new Create();

        Creature pier = new Creature("pier", 1, 1, 1), seb = new Creature("Sebas-chan", 2, 2, 2);
        pier.setOwner(new Player("Clarusso", null));
        seb.setOwner(new Player("Clarusso", null));
        pier.setOriginCard(new Card(1, "Pier", CardType.CREATURE, 12));
        seb.setOriginCard(new Card(2, "Seb", CardType.CREATURE, 13));

        create.setCreatures(new Creature[]{pier, seb});
        create.setPositions(new Position[]{new Position(new Line(1), 1), new Position(new Line(2), 2)});
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
        }
    }

    public static Game parseJsonGame(String file) {
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
            GameJsonParser gameJsonParser = new GameJsonParser(allCards, "src/main/resources/json/");
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
                create.setCreatures(new Creature[]{creature});
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
            cards.add(c);
        }

        return cards;
    }
}
