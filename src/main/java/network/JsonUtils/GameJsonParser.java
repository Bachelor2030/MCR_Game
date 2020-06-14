package network.JsonUtils;

import gameLogic.Commands.CreateTrap;
import gameLogic.Receptors.Creature;
import gameLogic.Receptors.Player;
import gameLogic.Invocator.Card.Card;
import gameLogic.Commands.CommandName;
import gameLogic.Commands.ConcreteCommand;
import gameLogic.Commands.OnLiveReceptors.OnCreature.Create;
import gameLogic.Game;
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
        Game game = new Game(nbr_lines, nbr_spots);

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
        game.initGame(p1, p2);

        return game;
    }

    private void setOwner(ArrayList<Card> cards, Player player) {
        for (Card card : cards) {
            for (ConcreteCommand command : card.getCommand().getCommands()) {
                if (command.getClass() == CreateTrap.class) {
                    ((CreateTrap)command).setPlayer(player);
                }
                if (command.getName() == CommandName.CREATE_CREATURE) {
                    for (Creature c : ((Create)command).getCreatures()) {
                        c.setOwner(player);
                    }
                }
            }
        }
    }
}
