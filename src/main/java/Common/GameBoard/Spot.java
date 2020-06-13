package Common.GameBoard;

import Client.Maths.Vector2f;
import Common.Receptors.Trap;
import Server.Game.ModelClasses.Receptor;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/** Cette classe représente une case qui constitue une ligne de combat */
public class Spot extends Application {
  // un case est représentée par un numéro
  private final int number;

  // Compteur de case
  private static int spotCounter = 0;

  // l'éventuelle créature présente sur la case
  private Receptor occupant;

  //l'image représentant le spot
  private FileInputStream imagePath = new FileInputStream("src/main/java/Common/GameBoard/island.png");
  Image image;
  ImageView imageView;

  Group root;

  // la position du spot dans l'espace
  protected Vector2f pos;

  private int HORIZONTAL_DISTANCE_BETWEEN_ISLANDS = 65;
  private int VERTICAL_DISTANCE_BETWEEN_ISLANDS = 120;
  private static float STARTING_COORDINATE_X = (float)Screen.getPrimary().getVisualBounds().getWidth() / 4;
  private static float STARTING_COORDINATE_Y = (float)Screen.getPrimary().getVisualBounds().getHeight() / 6;
  private static float MIN_WIDTH_RATIO = 0.6f;


  /**
   * Permet de construire un îlot
   * @param root : le groupe d'îlots qu'on affichera par la suite
   * @throws IOException
   */
  public Spot(Group root) throws IOException {
    this(spotCounter++, new Vector2f(STARTING_COORDINATE_X, STARTING_COORDINATE_Y), root);
  }

  /**
   * Permet de construire un ilôt avec une position et un nombre le définissant
   * @param number : allant de 0 à 9
   * @param pos : sa position dans la fenêtre du jeu
   * @param root : le groupe d'îlots qu'on affichera par la suite
   * @throws FileNotFoundException
   */
  public Spot(int number, Vector2f pos, Group root) throws FileNotFoundException {
    this.root = root;
    image = new Image(imagePath);
    imageView = new ImageView(image);
    this.number = number % 10;
    this.pos = pos;

    initSpot();
  }

  /**
   * Permet de choisir le bon facteur pour adapter la distance verticale entre les îles.
   * @param absoluteNumber : le nombre d'îles créées jusqu'à maintenant.
   * @return le facteur selon le nombre absolu d'île.
   */
  private int chooseFactor(int absoluteNumber) {
    if(absoluteNumber > 10 && absoluteNumber < 21)
    {
      return 1;
    }
    else if(absoluteNumber > 20 && absoluteNumber < 31)
    {
      return 2;
    }
    else if(absoluteNumber > 30)
    {
      return 3;
    }

    return 0;
  }

  /**
   * permet d'initialiser correctement la place d'un îlot
   */
  private void initSpot() {
    pos.x = (this.number % 10 == 0 ? (int)STARTING_COORDINATE_X : pos.x);
    pos.x += HORIZONTAL_DISTANCE_BETWEEN_ISLANDS * (number % 10);
    pos.y += (VERTICAL_DISTANCE_BETWEEN_ISLANDS * chooseFactor(spotCounter));
    System.out.println("position spot " + this.number + " : " + pos.x + ' ' + pos.y);
    root.getChildren().add(imageView);

    imageView.setFitWidth(image.getWidth() * MIN_WIDTH_RATIO);
    imageView.setFitHeight(image.getHeight() * MIN_WIDTH_RATIO);

    imageView.setX(pos.x);
    imageView.setY(pos.y);
  }

  @Override
  public void start(Stage stage) throws Exception {

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

  /**
   * @return l'image d'un spot (ici une petite île)
   */
  public FileInputStream getImagePath() {
    return imagePath;
  }

  public boolean isTrapped() {
    return occupant != null && occupant.getClass() == Trap.class;
  }
}