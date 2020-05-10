package GameBoard;

import java.util.LinkedList;

/**
 * Cette classe représente le "plateau" où se déplacent les différentes créatures lors d'une partie.
 * Une plateau comporte 4 lignes.
 */
public class Board {

    //le nombre de lignes
    private final int NB_LINES = 4;
    //compteur de ligne
    private static int lineCounter;

    //les lignes du board
    private LinkedList<Line> lines;

    /**
     * Constructeur de la classe Board
     */
    public Board()
    {
        lineCounter = 0;
        lines = new LinkedList<>();
        for(int line = 0; line < NB_LINES; ++line)
        {
            lines.add(new Line(++lineCounter));
        }
    }

    public LinkedList<Line> getLines() {
        return lines;
    }

    public int getNB_LINES() {
        return NB_LINES;
    }
}
