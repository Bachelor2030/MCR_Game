package Server.Game;

import Common.Receptors.Player;

/**
 * Cette classe permet de modéliser le jeu.
 */
public class Game {
    private static final int
            NBR_CHESTS_TO_DESTROY = 2;
    private Player
            player1,
            player2;
    private int
            turn;

    /**
     * Constructeur de la classe Game
     * @param player1 : le joueur n°1
     * @param player2 : le joueur n°2
     */
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        turn = 0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    /**
     * Permet de commencer le jeu.
     */
    public void startGame() {
        while (!finished()) {
            System.out.println("Turn " + (++turn));

            //todo player1.sendyourturn
            player1.playTurn(turn);

            if(!finished()) {
                //todo player2.sendyourturn
                player2.playTurn(turn);
            }
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
}
