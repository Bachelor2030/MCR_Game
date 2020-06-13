package Server.Game.ModelClasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Receptor {
    protected String name;

    private String imagePath = "design/images/creatures/shark.gif";
    private Image image;
    private ImageView imageView;

    public Receptor(String name){
        this.name = name;
        initDisplay();
    }

    public Receptor() {
        this.name = "";
        initDisplay();
    }

    public String getName() {
        return name;
    }

    public abstract void playTurn(int turn);

    @Override
    public String toString() {
        return name;
    }

    private void initDisplay() {
        image = new Image(imagePath);
        imageView = new ImageView(image);
        imageView.setFitWidth(image.getWidth() * 0.2);
        imageView.setFitHeight(image.getHeight() * 0.2);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Image getImage() {
        return image;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        initDisplay();
    }

    public void setTo(Receptor receptor) {
        System.out.println("I am a new receptor");
        imagePath = receptor.imagePath;
        image = receptor.image;
        imageView = receptor.imageView;
    }
}
