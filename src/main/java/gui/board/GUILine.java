package gui.board;

import gui.receptors.GUIChest;
import gui.receptors.GUICreature;
import gui.receptors.GUIPlayer;
import gui.receptors.GUIReceptor;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import network.states.ClientSharedState;

import java.io.IOException;
import java.util.LinkedList;

/** Cette classe permet d'instancier les lignes composant le "plateau" de jeu. */
public class GUILine {
  // Le nombre d'îles dans une ligne
  private static final int NB_SPOTS = 12;

  // Le numéro de la ligne
  private final int noLine;

  // Les cases composant la ligne
  private LinkedList<GUISpot> guiSpots;

  // Le gridpane permettant de contenir les îles de la ligne
  private GridPane gridPane;

  /**
   * Constructeur de ligne
   *
   * @param noLine : l'index de la ligne (0 à NB_LINES-1)
   */
  public GUILine(int noLine) {
    this.noLine = noLine;
  }

  /**
   * Constructeur de la classe GUILine
   *
   * @param noLine : le numéro de la ligne
   */
  public GUILine(
      int noLine,
      GridPane gridPane,
      VBox vbox,
      GUIPlayer player1,
      GUIPlayer player2,
      ClientSharedState clientSharedState)
      throws IOException {
    this.noLine = noLine;
    guiSpots = new LinkedList<>();
    this.gridPane = gridPane;

    //Permet d'initialiser toutes les îles contenues dans une ligne
    for (int spot = 0; spot < NB_SPOTS; ++spot) {
      vbox = new VBox(); // On créé une box verticale pour aligner la créature à l'île.
      guiSpots.add(new GUISpot(noLine, clientSharedState));
      if (spot == 0) {
        guiSpots.get(spot).setOccupant(new GUIChest("", player1));

      } else if (spot == 11) {
        guiSpots.get(spot).setOccupant(new GUIChest("", player2));

      } else {
        guiSpots.get(spot).setOccupant(new GUICreature());
      }

      vbox.setAlignment(Pos.CENTER);
      vbox.getChildren()
          .addAll(
              (guiSpots.get(spot).getOccupant().getImageView()), (guiSpots.get(spot).getButton()));
      gridPane.add(vbox, spot, noLine);
    }
  }

  public void setReceptor(GUIReceptor receptor, int spot) {
    ObservableList<Node> children = gridPane.getChildren();
    for (Node node : children) {
      if (gridPane.getRowIndex(node) == noLine && gridPane.getColumnIndex(node) == spot) {
        ImageView imageView = receptor.getImageView();
        imageView.setVisible(true); // please
        ((VBox) node).getChildren().set(0, imageView);
        break;
      }
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

  public GUISpot getSpot(int index) {
    if (guiSpots == null) return null;
    return guiSpots.get(index);
  }

  /**
   * Permet de récupérer la liste des cases de la ligne.
   *
   * @return la liste des cases de la ligne.
   */
  public LinkedList<GUISpot> getGuiSpots() {
    return guiSpots;
  }

  /**
   * Permet de récupérer le numéro de la ligne.
   *
   * @return le numéro de la ligne.
   */
  public int getNoLine() {
    return noLine;
  }

  public GUISpot getSpotAt(int position) {
    return guiSpots.get(position);
  }

  public GridPane getGridIslandPanel() {
    return gridPane;
  }

  public static int getNbSpots() {
    return NB_SPOTS;
  }
}
