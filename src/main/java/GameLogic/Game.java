package GameLogic;

import GameLogic.Board.Board;
import GameLogic.Commands.PlayersAction.PlayersAction;
import GameLogic.Invocator.Card.Card;
import GameLogic.Receptors.Player;
import GameLogic.Receptors.Receptor;
import Network.JsonUtils.JsonUtil;
import Network.ServerAdapter;
import Network.States.ServerState;
import javafx.concurrent.WorkerStateEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Cette classe permet de modéliser le jeu.
 */
public class Game extends Receptor {
    private static final int
            NBR_CHESTS_TO_DESTROY = 2;
    private Player
            player1,
            player2;
    private int
            turn,
            firstPlayerId;
    private Board board;
    private ServerAdapter serverAdapter;

    public Game(ServerAdapter serverAdapter, int nbr_lines, int nbr_spots) {
        this.serverAdapter = serverAdapter;
        this.board = new Board(nbr_lines, nbr_spots);
        turn = 0;
        if (Math.random() < 0.5) {
            firstPlayerId = 1;
        } else {
            firstPlayerId = 2;
        }
    }

    public void initGame(Player player1, Player player2) {
        if (firstPlayerId == 1) {
            this.player1 = player1;
            this.player2 = player2;
        } else {
            this.player1 = player2;
            this.player2 = player1;
        }
        serverAdapter.getServerState().setFinishedInit();
    }

    public void nextTurn() {
        System.out.println("Turn " + (++turn));
    }

    /**
     * Permet de terminer le jeu
     * @param winner : le joueur gagnant
     * @param looser : le joueur perdant
     */
    public void gameOver(Player winner, Player looser)
    {
        System.out.println(winner.getName() + " a gagné en ouvrant " + looser.getNbChestsDestroyed() + " coffres !\nBouuuh t'es un looser, " + looser.getName() + " !");
        //exit(0);
    }

    /**
     * Permet de savoir si le jeu est terminé.
     * Le jeu est terminé si l'un des joueurs à épuiser ses points de vie, ou si l'un des deux joueurs à détruit
     * au moins deux coffres de l'adversaire.
     * Permet de savoir si le jeu est terminé.
     * @return true si le jeu est terminé, false sinon.
     */
    private boolean finished() {
        if(player2.getNbChestsDestroyed() >= NBR_CHESTS_TO_DESTROY || player2.hasAbandoned())
        {
            gameOver(player1, player2);
            return true;
        }
        else if(player1.getNbChestsDestroyed() >= NBR_CHESTS_TO_DESTROY || player1.hasAbandoned())
        {
            gameOver(player2, player1);
            return true;
        }
        return false;
    }

    public int getTurn() {
        return turn;
    }


    public JSONObject initState(int playerId) {
        JSONObject gameJSON = new JSONObject();
        try {
            Player player = (playerId == firstPlayerId ? player1 : player2);

            gameJSON.put("type","init");
            JSONObject initJSON = new JSONObject();
            initJSON.put("lines", board.getNbLines());
            initJSON.put("linelength", board.getLine(0).getNbSpots());

            if (playerId == firstPlayerId) {
                initJSON.put("enemyname", player2.getName());
                initJSON.put("turn", "Your turn");
            } else {
                initJSON.put("enemyname", player1.getName());
                initJSON.put("turn", "Wait turn");
            }

            JSONArray cardsJSON = new JSONArray();
            for (Card card : player.getHand()) {
                cardsJSON.put(card.toJSON());
            }
            initJSON.put("cards", cardsJSON);

            gameJSON.put("init", initJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gameJSON;
    }

    public int getFirstPlayerId() {
        return firstPlayerId;
    }

    public boolean playerSentMessage(int playerId, String receivedMessage) {
        //TODO: If true, put json updates in serverAdapter.serverState.pushJsonToSend
        Player player = (playerId == firstPlayerId ? player1 : player2);

        PlayersAction action = new JsonUtil().getPlayerAction(receivedMessage);
        player.playTurn(turn, action);

        return true;
    }

    @Override
    public void playTurn(int turn, PlayersAction action) {}
}
