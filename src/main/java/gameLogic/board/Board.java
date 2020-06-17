package gameLogic.board;

import java.util.LinkedList;

public class Board {

  // le nombre de lignes
  private final int NB_LINES;

  // les lignes du board
  private LinkedList<Line> lines;

  /** Constructeur de la classe board */
  public Board(int nbr_lines, int nbr_spots) {
    NB_LINES = nbr_lines;
    lines = new LinkedList<>();
    for (int line = 0; line < NB_LINES; ++line) {
      lines.add(new Line(line, nbr_spots));
    }
  }

  public Line getLine(int index) {
    return lines.get(index);
  }

  public int getNbLines() {
    return lines.size();
  }

  public Spot getPosition(int line, int spot) {
    return lines.get(line).getSpot(spot);
  }
}
