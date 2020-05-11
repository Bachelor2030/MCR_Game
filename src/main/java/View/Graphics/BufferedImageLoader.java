package View.Graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Permet de manage les différentes images à afficher
 */
public class BufferedImageLoader {
    private BufferedImage image;

    /**
     *
     * @param path : le chemin de l'image
     * @return l'image loadée.
     */
    public BufferedImage loadImage(String path) throws IOException {
        image = ImageIO.read(getClass().getResource(path));
        return image;
    }
}
