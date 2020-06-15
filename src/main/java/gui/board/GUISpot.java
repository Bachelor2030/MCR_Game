package gui.board;

import gui.maths.Vector2f;
import gameLogic.receptors.Trap;
import gameLogic.receptors.Receptor;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/** Cette classe représente une case qui constitue une ligne de combat */
public class GUISpot extends Application {
  // un case est représentée par un numéro
  private final int number;

  // Compteur de case
  private static int spotCounter = 0;

  // l'éventuelle créature présente sur la case
  private Receptor occupant;

  // l'image représentant le spot
  private FileInputStream imagePath =
      new FileInputStream("src/main/resources/design/images/field/island.png");
  Image image;
  ImageView imageView;

  // la position du spot dans l'espace
  protected Vector2f pos;

  private static float STARTING_COORDINATE_X =
      (float) Screen.getPrimary().getVisualBounds().getWidth() / 4;
  private static float STARTING_COORDINATE_Y =
      (float) Screen.getPrimary().getVisualBounds().getHeight() / 6;
  private static float MIN_WIDTH_RATIO = 0.6f;

  /**
   * Permet de construire un îlot
   *
   * @throws IOException
   */
  public GUISpot() throws IOException {
    this(spotCounter++, new Vector2f(STARTING_COORDINATE_X, STARTING_COORDINATE_Y));
  }

  /**
   * Permet de construire un ilôt avec une position et un nombre le définissant
   *
   * @param number : allant de 0 à 9
   * @param pos : sa position dans la fenêtre du jeu
   * @throws FileNotFoundException
   */
  private GUISpot(int number, Vector2f pos) throws FileNotFoundException {
    image = new Image(imagePath);
    imageView = new ImageView(image);
    this.number = number % 10;
    this.pos = pos;
    initDisplaySpot();
  }

  /** permet d'initialiser correctement la place d'un îlot */
  private void initDisplaySpot() {
    imageView.setFitWidth(image.getWidth() * MIN_WIDTH_RATIO);
    imageView.setFitHeight(image.getHeight() * MIN_WIDTH_RATIO);

    //imageView.setX(pos.x);
    //imageView.setY(pos.y);
  }

  @Override
  public void start(Stage stage) throws Exception {}

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

  /** @return l'image d'un spot (ici une petite île) */
  public FileInputStream getImagePath() {
    return imagePath;
  }

  public ImageView getImageView() {
    return imageView;
  }
}
