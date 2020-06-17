package gui.receptors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GUIChest extends GUIReceptor {
  private GUIPlayer owner;

  public GUIChest(String name, GUIPlayer owner) {
    super(name, "src/main/resources/design/images/treasure.png");
    this.owner = owner;
  }

  @Override
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
