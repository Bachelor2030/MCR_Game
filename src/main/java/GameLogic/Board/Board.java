package GameLogic.Board;

import GameLogic.Receptors.Receptor;
import javafx.scene.Group;

import java.io.IOException;
import java.util.LinkedList;

public class Board {

    //le nombre de lignes
    private final int NB_LINES;

    //les lignes du board
    private LinkedList<Line> lines;

    Group root;

    /**
     * Constructeur de la classe Board
     */
    public Board(int nbr_lines, int nbr_spots) throws IOException {
        NB_LINES = nbr_lines;
        lines = new LinkedList<>();
        for(int line = 0; line < NB_LINES; ++line) {
            lines.add(new Line(line, nbr_spots));
        }
    }

    public void place(Receptor receptor, int lineCounter, int spot) {
        // TODO lines.get(lineCounter).setReceptor(receptor, spot);
    }

    public Line getLine(int index) {
        if (index < lines.size()) {
            return lines.get(index);
        }
        return null;
    }

    /**
     * Permet de récupérer la liste des lignes du board.
     * @return la liste des lignes du board.
     */
    public LinkedList<Line> getLines() {
        return lines;
    }

    /**
     * Permet de savoir le nombre de lignes constituant un board.
     * @return le nombre de lignes constituant un board.
     */
    public int getNB_LINES() {
        return NB_LINES;
    }
}
