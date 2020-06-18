package gui.receptors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Cette classe permet de modéliser un receptor
 */
public abstract class GUIReceptor {
  protected String name; // le nom du receptor
  protected String imagePath; //le chemin de l'image du receptor

  /**
   * Constructeur de la classe receptor
   * @param name : le nom du receptor
   * @param imagePath : le chemin de l'image du receptor
   */
  public GUIReceptor(String name, String imagePath) {
    this.name = name;
    this.imagePath = imagePath;
  }

  /**
   * @return le nom du receptor
   */
  public String getName() {
    return name;
  }

  /**
   * @return le chemin de l'image du receptor
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * @return l'imageView du receptor
   */
  public ImageView getImageView() {
    Image image;
    ImageView imageView = null;
    try {
      image = new Image(new FileInputStream(imagePath));
      imageView = new ImageView(image);
      imageView.setFitWidth(image.getWidth() * 0.2);
      imageView.setFitHeight(image.getHeight() * 0.2);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return imageView;
  }

  /**
   * Permet de modifier le chemin de l'image du receptor
   * @param imagePath : le nouveau chemin
   */
  public void setImgPath(String imagePath) {
    this.imagePath = imagePath;
  }

  /**
   * Permet de remplacer le receptor
   * @param receptor : le receptor remplacant
   */
  public void setTo(GUIReceptor receptor) {
    if (receptor == null) {
      imagePath = "src/main/resources/design/images/creatures/empty.jpg";
    } else {
      imagePath = receptor.getImagePath();
    }
  }
}
