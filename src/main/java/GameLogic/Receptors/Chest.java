package GameLogic.Receptors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Chest owned by a player. Said player must protect it by using creatures and spells
 */
public class Chest extends LiveReceptor {
    // Max life points for a chest
    private static final int CHEST_LIFE_POINTS = 5;
    private FileInputStream imagePath = new FileInputStream("src/main/resources/design/images/treasure.png");
    private Image image;
    private ImageView imageView;

    /**
     * Create a chest with the given name and owner
     * @param name the name of the current chest
     * @param owner the player that owns the current chest
     */
    public Chest(String name, Player owner) throws FileNotFoundException {
        super(name, CHEST_LIFE_POINTS, "Chest");
        initDisplayChest();
    }

    private void initDisplayChest() {
        image = new Image(imagePath);
        imageView = new ImageView(image);
        imageView.setFitWidth(image.getWidth() * 0.12);
        imageView.setFitHeight(image.getHeight() * 0.12);
    }

    /**
     * Checks if the current chest is closed
     * A chest is opened if it has no lifePoints left
     * @return true if the chest still has life points left
     */
    public boolean isClosed() {
        return isAlive();
    }

    @Override
    public void playTurn(int turn) {}

    public ImageView getImageView() {
        return imageView;
    }
}
