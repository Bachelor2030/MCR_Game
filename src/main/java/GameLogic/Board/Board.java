package GameLogic.Board;

import GameLogic.Receptors.Player;
import GameLogic.Receptors.Receptor;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Cette classe représente le "plateau" où se déplacent les différentes créatures lors d'une partie.
 * Une plateau comporte 4 lignes.
 */
public class Board {

    //TODO : rentre paramétrique.
    //le nombre de lignes
    private final int NB_LINES = 4;

    //compteur de ligne
    private static int lineCounter;

    //les lignes du board
    private LinkedList<BoardLine> boardLines;

    Group root;

    /**
     * Constructeur de la classe Board
     */
    public Board(GridPane gridPane, VBox vbox, Player player1, Player player2) throws IOException {
        lineCounter = 0;
        boardLines = new LinkedList<BoardLine>();
        for(int line = 0; line < NB_LINES; ++line) {
            boardLines.add(new BoardLine(++lineCounter, gridPane, vbox, player1, player2));
        }
    }

    public void place(Receptor receptor, int lineCounter, int spot) {
        boardLines.get(lineCounter).setReceptor(receptor, spot);
    }

    public BoardLine getLine(int index) {
        if (index < boardLines.size()) {
            return boardLines.get(index);
        }
        return null;
    }

    /**
     * Permet de récupérer la liste des lignes du board.
     * @return la liste des lignes du board.
     */
    public LinkedList<BoardLine> getBoardLines() {
        return boardLines;
    }

    /**
     * Permet de savoir le nombre de lignes constituant un board.
     * @return le nombre de lignes constituant un board.
     */
    public int getNB_LINES() {
        return NB_LINES;
    }
}
