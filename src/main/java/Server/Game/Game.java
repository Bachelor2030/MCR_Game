package Server.Game;

import Common.GameBoard.Board;
import Common.Receptors.Player;

/**
 * Cette classe permet de modéliser le jeu.
 */
public class Game {
    private static final int NBR_CHESTS_TO_DESTROY = 2;
    private Player player1, player2;

    private Board board;
    private int turn;

    /**
     * Constructeur de la classe Game
     * @param player1 : le joueur n°1
     * @param player2 : le joueur n°2
     * @param board : le plateau sur lequel on joue.
     */
    public Game(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        turn = 0;
    }

    /**
     * Permet de passer au tour suivant.
     */
    private void nextTurn() {
        ++turn;
        player1.playTurn(turn);

        if(!finished()) {
            player2.playTurn(turn);
        }
    }

    /**
     * Permet de commencer le jeu.
     */
    public void startGame() {
        while (!finished()) {
            nextTurn();
        }
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
        if(player2.getNbChestsDestroyed() >= NBR_CHESTS_TO_DESTROY)
        {
            gameOver(player1, player2);
            return true;
        }
        else if(player1.getNbChestsDestroyed() >= NBR_CHESTS_TO_DESTROY)
        {
            gameOver(player2, player1);
            return true;
        }
        return false;
    }
}
