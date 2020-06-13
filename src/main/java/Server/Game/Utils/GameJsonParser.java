package Server.Game.Utils;

import Common.Receptors.Player;
import Server.Game.Card.Card;
import Server.Game.ModelClasses.Commands.CommandName;
import Server.Game.ModelClasses.ConcreteCommand;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature.Create;
import Server.Game.Game;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameJsonParser {
    private final String path;
    private final ArrayList<Card> allCards;

    public GameJsonParser(ArrayList<Card> allCards, String path) {
        this.allCards = allCards;
        this.path = path;
    }

    public Game parseJson(String json) throws JSONException, FileNotFoundException {
        JSONObject obj = new JSONObject(json);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        System.out.println("Reading " + pageName);

        String player1 = obj.getJSONObject("playerNames").getString("player1");
        String player2 = obj.getJSONObject("playerNames").getString("player2");

        String cards1 = obj.getJSONObject("cardsPlayer1").getString("file");
        String cards2 = obj.getJSONObject("cardsPlayer2").getString("file");

        ArrayList<Card> cardsPlayer1, cardsPlayer2;

        CardJsonParser cardJsonParser = new CardJsonParser();

        cardsPlayer1 = cardJsonParser.parseJson(path + cards1, allCards);
        cardsPlayer2 = cardJsonParser.parseJson(path + cards2, allCards);

        Player p1 = new Player(player1, cardsPlayer1);
        Player p2 = new Player(player2, cardsPlayer2);

        for (Card card : cardsPlayer1) {
            for (ConcreteCommand command :
                    card.getCommand().getCommands()) {
                if (command.getName() == CommandName.CREATE_CREATURE) {
                    ((Create)command).getCreature().setOwner(p1);
                }
            }
        }

        for (Card card : cardsPlayer2) {
            for (ConcreteCommand command :
                    card.getCommand().getCommands()) {
                if (command.getName() == CommandName.CREATE_CREATURE) {
                    ((Create)command).getCreature().setOwner(p2);
                }
            }
        }

        return new Game(p1, p2);
    }
}
