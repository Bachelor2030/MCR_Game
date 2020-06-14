package gui.buttons;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UtilButton extends GameButton {
  private final int SIZE = 21;

  private Image image;
  private ImageView imageView;

  public UtilButton(String styleClass, String imagePath) {
    super("", styleClass);

    image = new Image(getClass().getResourceAsStream(imagePath));
    imageView = new ImageView(image);
    imageView.setFitHeight(SIZE);
    imageView.setFitWidth(SIZE);

    this.getButton().setGraphic(imageView);
    this.getButton().setAlignment(Pos.CENTER_RIGHT);
  }
}
