package Server.Game.Utils.Parsers;

import Common.Receptors.Player;
import Server.Game.Card.Card;
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
        cardsPlayer1 = cardParser(path + cards1);
        cardsPlayer2 = cardParser(path + cards2);

        Player p1 = new Player(player1, cardsPlayer1);
        Player p2 = new Player(player2, cardsPlayer2);

        // TODO remove null and replace with correct root
        return new Game(p1, p2);
    }

    private static ArrayList<Card> cardParser(String file) {
        ArrayList<Card> cards = null;
        try {
            String playersCards = getJsonContent(file);

            System.out.println("Read " + file);
            cards = CardsJsonParser.parseJson(playersCards, PATH);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
