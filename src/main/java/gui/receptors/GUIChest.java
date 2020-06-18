package gui.receptors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/** Représentation d'un coffre pour la GUI */
public class GUIChest extends GUIReceptor {
  private GUIPlayer owner; // le player détenant le coffre

  /**
   * Constructeur de la classe
   *
   * @param name : le nom du coffre
   * @param owner : le player détenant le coffre
   */
  public GUIChest(String name, GUIPlayer owner) {
    super(name, "src/main/resources/design/images/treasure.png");
    this.owner = owner;
  }

  @Override
  /** Permet de récupérer et de manipuler l'imageView du coffre. */
  public ImageView getImageView() {
    Image image = null;
    ImageView imageView = null;
    try {
      image = new Image(new FileInputStream(imagePath));
      imageView = new ImageView(image);
      imageView.setFitWidth(image.getWidth() * 0.12);
      imageView.setFitHeight(image.getHeight() * 0.12);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return imageView;
  }
}
