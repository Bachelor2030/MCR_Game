package Server.Game.Utils;

import Common.Receptors.Player;
import Server.Game.Card.Card;
import Server.Game.Card.Commands.CommandName;
import Server.Game.Card.Commands.ConcreteCommand;
import Server.Game.Card.Commands.CreateCreature;
import Server.Game.Game;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class GameJsonParser {
    private static String PATH;
    public static Game parseJson(String json, String path) throws JSONException {
        PATH = path;
        JSONObject obj = new JSONObject(json);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println("Reading " + pageName);

        String player1 = obj.getJSONObject("playerNames").getString("player1");
        String player2 = obj.getJSONObject("playerNames").getString("player2");

        String cards1 = obj.getJSONObject("cardsPlayer1").getString("file");
        String cards2 = obj.getJSONObject("cardsPlayer2").getString("file");

        ArrayList<Card> cardsPlayer1, cardsPlayer2;
        cardsPlayer1 = Card.parseJson(path + cards1);
        cardsPlayer2 = Card.parseJson(path + cards2);

        Player p1 = new Player(player1, cardsPlayer1);
        Player p2 = new Player(player2, cardsPlayer2);

        for (Card card : cardsPlayer1) {
            for (ConcreteCommand command :
                    card.getCommand().getCommands()) {
                if (command.getName() == CommandName.CREATE_CREATURE) {
                    ((CreateCreature)command).getCreature().setOwner(p1);
                }
            }
        }

        for (Card card : cardsPlayer2) {
            for (ConcreteCommand command :
                    card.getCommand().getCommands()) {
                if (command.getName() == CommandName.CREATE_CREATURE) {
                    ((CreateCreature)command).getCreature().setOwner(p2);
                }
            }
        }

        return new Game(p1, p2);
    }

    private static ArrayList<Card> cardParser(String file) {
        ArrayList<Card> cards = null;
        String playersCards = getJsonContent(file);

        System.out.println("Read " + file);
        //cards = CardsJsonParser.parseJson(playersCards, PATH);

        return cards;
    }

    private static String getJsonContent(String file) {
        StringBuilder sb = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

            sb = new StringBuilder();
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
