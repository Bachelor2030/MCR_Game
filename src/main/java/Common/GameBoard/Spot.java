package Common.GameBoard;

import Client.Maths.Vector2f;
import Common.Receptors.Trap;
import Server.Game.ModelClasses.Receptor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/** Cette classe représente une case qui constitue une ligne de combat */
public class Spot {
  // un case est représentée par un numéro
  private final int number;

  // Compteur de case
  private static int spotCounter = 0;

  // l'éventuelle créature présente sur la case
  private Receptor occupant;
  private FileInputStream image = new FileInputStream("src/main/java/Common/GameBoard/island.png");

  // la position du spot dans l'espace
  protected Vector2f pos;

  /** Constructeur de la classe Spot. */
  public Spot() throws IOException {
    this(++spotCounter, new Vector2f());
  }

  public Spot(int number, Vector2f pos) throws FileNotFoundException {
    this.number = number;
    this.pos = pos;
  }

  /**
   * Permet de savoir si une case est occupée par une créature.
   *
   * @return true si occupée, false sinon.
   */
  public boolean isEmpty() {
    return occupant == null || occupant.getClass() == Trap.class;
  }

  /**
   * Permet de set l'éventuelle créature présente sur la case.
   *
   * @param occupant : la créature
   */
  public void setOccupant(Receptor occupant) {
    this.occupant = occupant;
  }

  /** Modélise le départ d'une créature de la case. */
  public void leave() {
    this.occupant = null;
  }

  /**
   * Permet de récupérer l'éventuelle créature présente sur la case.
   *
   * @return la créature, si elle occupe la case. Sinon null.
   */
  public Receptor getOccupant() {
    return occupant;
  }

  public FileInputStream getImage() {
    return image;
  }

  public boolean isTrapped() {
    return occupant != null && occupant.getClass() == Trap.class;
  }
}
