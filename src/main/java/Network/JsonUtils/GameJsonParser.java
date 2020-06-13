package Network.JsonUtils;

import GameLogic.Board.Board;
import GameLogic.Receptors.Creature;
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
    private final Board board;

    public GameJsonParser(ArrayList<Card> allCards, String path, Board board) {
        this.allCards = allCards;
        this.path = path;
        this.board = board;
    }

    public Game parseJson(String json) throws JSONException, FileNotFoundException {
        JSONObject obj = new JSONObject(json);

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

        setOwner(cardsPlayer1, p1);
        setOwner(cardsPlayer2, p2);

        return new Game(p1, p2, board);
    }

    private void setOwner(ArrayList<Card> cards, Player player) {
        for (Card card : cards) {
            for (ConcreteCommand command : card.getCommand().getCommands()) {
                if (command.getName() == CommandName.CREATE_CREATURE) {
                    for (Creature c : ((Create)command).getCreatures()) {
                        c.setOwner(player);
                    }
                }
            }
        }
    }
}
