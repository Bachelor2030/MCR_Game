package Network.JsonUtils;

import GameLogic.Commands.CreateTrap;
import GameLogic.Receptors.Player;
import GameLogic.Invocator.Card.Card;
import GameLogic.Commands.CommandName;
import GameLogic.Commands.ConcreteCommand;
import GameLogic.Commands.OnLiveReceptors.OnCreature.Create;
import GameLogic.Game;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameJsonParser {
    private final String path;
    private final ArrayList<Card> allCards;
    private final int nbr_lines, nbr_spots;

    public GameJsonParser(ArrayList<Card> allCards, String path, int nbr_lines, int nbr_spots) {
        this.allCards = allCards;
        this.path = path;
        this.nbr_lines = nbr_lines;
        this.nbr_spots = nbr_spots;
    }

    public Game parseJson(String json) throws JSONException, FileNotFoundException {
        JSONObject obj = new JSONObject(json);
        Game game = null;

        String player1 = obj.getJSONObject("playerNames").getString("player1");
        String player2 = obj.getJSONObject("playerNames").getString("player2");

        // TODO: je saurais ce qu'il faut faire

        String cards1 = obj.getJSONObject("cardsPlayer1").getString("file");
        String cards2 = obj.getJSONObject("cardsPlayer2").getString("file");

        ArrayList<Card> cardsPlayer1, cardsPlayer2;

        CardJsonParser cardJsonParser = new CardJsonParser();

        cardsPlayer1 = cardJsonParser.parseJson(path + cards1, allCards);
        cardsPlayer2 = cardJsonParser.parseJson(path + cards2, allCards);

        Player p1 = new Player(player1, cardsPlayer1, game);
        Player p2 = new Player(player2, cardsPlayer2, game);

        game.initGame(p1, p2);

        return game;
    }

}
