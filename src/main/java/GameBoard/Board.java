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
    private static int lineCounter = 0;

    //les lignes du board
    private LinkedList<Line> lines;

    /**
     * Constructeur de la classe Board
     */
    public Board()
    {
        lines = new LinkedList<Line>();
        for(int line = 0; line < NB_LINES; ++line)
        {
            lines.add(new Line(++lineCounter));
        }
    }
}
