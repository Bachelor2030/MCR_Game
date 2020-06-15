package gameLogic;

import gameLogic.board.Board;
import gameLogic.commands.playersAction.PlayersAction;
import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
import gameLogic.receptors.Receptor;
import network.jsonUtils.JsonUtil;
import network.Messages;
import network.ServerAdapter;
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

            gameJSON.put(Messages.JSON_TYPE, Messages.JSON_TYPE_INIT);
            JSONObject initJSON = new JSONObject();
            initJSON.put(Messages.JSON_TYPE_LINE, board.getNbLines());
            initJSON.put(Messages.JSON_TYPE_SPOT, board.getLine(0).getNbSpots());

            if (playerId == firstPlayerId) {
                initJSON.put(Messages.JSON_TYPE_ENEMYNAME, player2.getName());
                initJSON.put(Messages.JSON_TYPE_TURN, Messages.JSON_TYPE_YOUR_TURN);
            } else {
                initJSON.put(Messages.JSON_TYPE_ENEMYNAME, player1.getName());
                initJSON.put(Messages.JSON_TYPE_TURN, Messages.JSON_TYPE_WAIT_TURN);
            }

            JSONArray cardsJSON = new JSONArray();
            for (Card card : player.getHand()) {
                cardsJSON.put(card.toJSON());
            }
            initJSON.put(Messages.JSON_TYPE_CARDS, cardsJSON);

            gameJSON.put(Messages.JSON_GAMESTATE, initJSON);
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
