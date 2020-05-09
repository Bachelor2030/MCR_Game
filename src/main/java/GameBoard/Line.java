package GameBoard;

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

    Line(int noLine) {
        this.noLine = noLine;
        spots = new LinkedList<Spot>();
        for(int spot = 0; spot <NB_SPOTS ; ++spot)
        {
            spots.add(new Spot());
        }
    }
}
