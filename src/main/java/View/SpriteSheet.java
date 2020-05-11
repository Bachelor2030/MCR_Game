package View;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage image;

    /**
     * Constructeur de la classe SpriteSheet
     * @param image : l'image (spriteSheet)
     */
    public SpriteSheet(BufferedImage image)
    {
        this.image = image;
    }

    /**
     * Permet de crop les différentes parties de la spritsheet
     * @param col : le nombre de colonnes de la sheet.
     * @param row : le nombre de ligne de la sheet.
     * @param width : la largeur de la sheet.
     * @param height : la hauteur de la sheet.
     * @return l'image générée.
     */
    public BufferedImage grabImage(int col, int row, int width, int height)
    {
        BufferedImage img = image.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
        return img;
    }
}
