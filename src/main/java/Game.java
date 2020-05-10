import GameBoard.Board;
import Player.Player;

/**
 * Cette classe permet de modéliser le jeu.
 */
public class Game {
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
        // TODO : do actions
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
     * Permet de savoir si le jeu est terminé.
     * @return true si le jeu est terminé, false sinon.
     */
    private boolean finished() {
        // TODO : if a player has no lifePoints left, the game is finished
        return false;
    }
}
