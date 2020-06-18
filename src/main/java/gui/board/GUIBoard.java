package gui.board;

import gui.receptors.GUIPlayer;
import gui.receptors.GUIReceptor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Cette classe représente le "plateau" où se déplacent les différentes créatures lors d'une partie.
 * Une plateau comporte 4 lignes.
 */
public class GUIBoard {

  // le nombre de lignes
  private static final int NB_LINES = 4;

  // compteur de ligne
  private static int lineCounter;

  // les lignes du board
  private LinkedList<GUILine> guiLines;

  /**
   * Constructeur de la classe GUIBoard
   */
  public GUIBoard(GridPane gridPane, VBox vbox, GUIPlayer player1, GUIPlayer player2)
      throws IOException {
    lineCounter = 0;
    guiLines = new LinkedList<>();
    for (int line = 0; line < NB_LINES; ++line) {
      guiLines.add(
          new GUILine(
              lineCounter++, gridPane, vbox, player1, player2, player1.getClientSharedState()));
    }
  }

  /**
   * Place une créture, un trap ou un spell sur une île renseignée à l'aide de la ligne
   * et du spot.
   * @param receptor : la créture, le trap ou le spell
   * @param lineCounter : le numéro de la ligne (0 à NB_LINES-1)
   * @param spot : le numéro de l'île (0 à NB_SPOT - 1)
   */
  public void place(GUIReceptor receptor, int lineCounter, int spot) {
    guiLines.get(lineCounter).setReceptor(receptor, spot);
    guiLines.get(lineCounter).getSpot(spot).setOccupant(receptor);
  }

  /**
   * Permet de récupérer l'une des lignes du board en spécifiant son index.
   *
   * @param index : l'index de la ligne (0 à NB_LINE - 1.)
   * @return la ligne spécifiée ou null s'il n'en a pas trouvà à l'index spécifié.
   */
  public GUILine getLine(int index) {
    if (index < guiLines.size()) {
      return guiLines.get(index);
    }
    return null;
  }

  /**
   * Permet de récupérer la liste des lignes du board.
   *
   * @return la liste des lignes du board.
   */
  public LinkedList<GUILine> getGuiLines() {
    return guiLines;
  }

  /**
   * Permet de savoir le nombre de lignes constituant un board.
   *
   * @return le nombre de lignes constituant un board.
   */
  public int getNB_LINES() {
    return NB_LINES;
  }

  /**
   * Permet de placer un trap aux coordonnées renseignées.
   *
   * @param line : le numéro de la ligne (0 à NB_LINES - 1)
   * @param position : le numéro de l'île (0 à NB_SPOT - 1)
   */
  public void placeTrap(int line, int position) {
    guiLines.get(line).getSpot(position).trap();
  }

  /**
   * Permet d'enlever un trap aux coordonnées renseignées.
   *
   * @param line : le numéro de la ligne (0 à NB_LINES - 1)
   * @param position : le numéro de l'île (0 à NB_SPOT - 1)
   */
  public void removeTrap(int line, int position) {
    guiLines.get(line).getSpot(position).unTrap();
  }

  /**
   * Permet de récupérer un spot particulier selon les coordonnées renseignées.
   * @param line : le numéro de la ligne (0 à NB_LINES - 1)
   * @param spot : le numéro de l'île (0 à NB_SPOT - 1)
   * @return le spot (l'île) souhaité.
   */
  public GUISpot getSpot(int line, int spot) {
    return guiLines.get(line).getSpot(spot);
  }

  /**
   * Permet de récupérer une ligne selon un nombre donné.
   * @param number : l'index de l'île (0 à NB_LIGNES-1)
   * @return l'île dont l'index est 'number'.
   */
  public GUILine getLineAt(int number) {
    return guiLines.get(number);
  }

  /**
   * Permet de récupérer une ligne sous forme de GridPane.
   * @param line : l'index de la ligne (0 à NB_LIGNES-1)
   * @return une ligne sous forme de GridPane
   */
  public GridPane getGridIslandPanel(int line) {
    return guiLines.get(line).getGridIslandPanel();
  }

  /**
   * Permet de récupérer toutes les îles présentes sur le board, sous forme d'ArrayList<GridPane>.
   *
   * @return la panel d'îles sous forme d'ArrayList<GridPane>
   */
  public ArrayList<GridPane> getGridIslandPanels() {
    ArrayList<GridPane> gridPanes = new ArrayList<>();
    for (GUILine line : guiLines) {
        gridPanes.add(line.getGridIslandPanel());
    }
    return gridPanes;
  }
}
