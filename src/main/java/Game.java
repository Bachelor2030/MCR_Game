import Player.Player;

public class Game {
    private Player player1, player2;
    private Board board;
    private int turn;

    public Game(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        turn = 0;
    }

    private void nextTurn() {
        ++turn;
        // TODO : do actions
    }

    public void startGame() {
        while (!finished()) {
            nextTurn();
        }
    }

    private boolean finished() {
        // TODO : if a player has no lifePoints left, the game is finished
        return false;
    }
}
