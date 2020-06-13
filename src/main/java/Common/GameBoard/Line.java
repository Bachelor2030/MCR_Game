package Common.GameBoard;

import javafx.scene.Group;

import java.io.IOException;
import java.util.LinkedList;

/** Cette classe permet d'instancier les lignes composant le "plateau" de jeu. */
public class Line {

  // Le nombre de cases dans une ligne
  private final int NB_SPOTS = 10;

  // Le numéro de la ligne
  private int noLine;

  // Les cases composant la ligne
  private LinkedList<Spot> spots;

  //le groupe d'îlots qu'on affichera par la suite
  Group root;

  public Line(int noLine) {
    this.noLine = noLine;
  }

  /**
   * Constructeur de la classe Line
   *
   * @param noLine : le numéro de la ligne
   * @param root : le groupe d'îlots qu'on affichera par la suite
   */
  public Line(int noLine, Group root) throws IOException {
    this.root = root;
    this.noLine = noLine;
    spots = new LinkedList<>();
    for (int spot = 0; spot < NB_SPOTS; ++spot) {
      spots.add(new Spot(root));
    }
  }

  /**
   * Permet de récupérer le nombre de cases.
   *
   * @return le nombre de cases de la ligne.
   */
  public int getNB_SPOTS() {
    return NB_SPOTS;
  }

  public Spot getSpot(int index) {
    if (spots == null)
      return null;
    return spots.get(index);
  }

  /**
   * Permet de récupérer la liste des cases de la ligne.
   *
   * @return la liste des cases de la ligne.
   */
  public LinkedList<Spot> getSpots() {
    return spots;
  }

  /**
   * Permet de récupérer le numéro de la ligne.
   *
   * @return le numéro de la ligne.
   */
  public int getNoLine() {
    return noLine;
  }
}
