package gui.receptors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GUIReceptor {
    protected String name;          // the name of the receptor
    protected String imagePath;

    public GUIReceptor(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImageView getImageView() {
        Image image = null;
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

    public void setImgPath(String imagePath) {
        this.imagePath = imagePath;
    }
}
