package Game.GameBoard;

import Game.GameBoard.Spot;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Cette classe permet d'instancier les lignes composant le "plateau" de jeu.
 */
public class Line {

    //Le nombre de cases dans une ligne
    private final int NB_SPOTS = 10;

    //Le numéro de la ligne
    private int noLine;

    //Les cases composant la ligne
    private LinkedList<Spot> spots;

    /**
     * Constructeur de la classe Line
     * @param noLine : le numéro de la ligne
     */
    public Line(int noLine) throws IOException {
        this.noLine = noLine;
        spots = new LinkedList<Spot>();
        for(int spot = 0; spot <NB_SPOTS ; ++spot)
        {
            spots.add(new Spot());
        }
    }

    /**
     * Permet de récupérer le nombre de cases.
     * @return le nombre de cases de la ligne.
     */
    public int getNB_SPOTS() {
        return NB_SPOTS;
    }

    /**
     * Permet de récupérer la liste des cases de la ligne.
     * @return la liste des cases de la ligne.
     */
    public LinkedList<Spot> getSpots() {
        return spots;
    }

    /**
     * Permet de récupérer le numéro de la ligne.
     * @return le numéro de la ligne.
     */
    public int getNoLine() {
        return noLine;
    }
}
