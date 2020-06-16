package gui.receptors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GUIChest {
    private String name;
    private GUIPlayer owner;

    public GUIChest(String name, GUIPlayer owner) {
        this.owner = owner;
        this.name = name;
    }

    public ImageView getImageView() {
        try {
            Image image = new Image(new FileInputStream("src/main/resources/design/images/treasure.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(image.getWidth() * 0.12);
            imageView.setFitHeight(image.getHeight() * 0.12);
            return imageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
