package Client.View.Graphics;

import Client.Maths.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Représente une feuille de "sprite".
 * Cela permet d'afficher des éléments qui bougent à l'arrêt (comme des gifs)
 */
public class SpriteSheet {
    private final int TILE_SIZE = 32;

    private Sprite SPRITESHEET = null;
    private Sprite[][] spriteArray;

    public int width;
    public int height;

    private int widthSprite;
    private int heightSprite;

    private String file;

    public static Client.View.Graphics.Font currentFont;

    /**
     * Constructeur de la classe SpriteSheet
     * @param file : l'image du sprite
     */
    public SpriteSheet(String file) {
        this.file = file;
        width = TILE_SIZE;
        height = TILE_SIZE;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = new Sprite(loadSprite(file));

        widthSprite = SPRITESHEET.image.getWidth() / width;
        heightSprite = SPRITESHEET.image.getHeight() / height;
        loadSpriteArray();
    }

    /**
     * Constructeur de la classe SpriteSheet
     * @param sprite : un objet sprite représentant l'élément
     * @param name : le nom de l'image (sert pour le débug)
     * @param width : la largeur de l'image
     * @param height : la hauteur de l'image
     */
    public SpriteSheet(Sprite sprite, String name, int width, int height) {
        this.width = width;
        this.height = height;

        System.out.println("Loading: " + name + "...");
        SPRITESHEET = sprite;

        widthSprite = SPRITESHEET.image.getWidth() / width;
        heightSprite = SPRITESHEET.image.getHeight() / height;
        loadSpriteArray();

    }

    /**
     * Constructeur de la classe SpriteSheet
     * @param file : l'image de l'élément
     * @param width : la largeur de l'image
     * @param height : la hauteur de l'image
     */
    public SpriteSheet(String file, int width, int height) {
        this.width = width;
        this.height = height;
        this.file = file;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = new Sprite(loadSprite(file));

        widthSprite = SPRITESHEET.image.getWidth() / width;
        heightSprite = SPRITESHEET.image.getHeight() / height;
        loadSpriteArray();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int i) {
        width = i;
        widthSprite = SPRITESHEET.image.getWidth() / width;
    }

    public void setHeight(int i) {
        height = i;
        heightSprite = SPRITESHEET.image.getHeight() / height;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getRows() { return heightSprite; }
    public int getCols() { return widthSprite; }
    public int getTotalTiles() { return widthSprite * heightSprite; }
    public String getFilename() { return file; }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("BLARGH_ERROR: could not load file: " + file);
        }
        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new Sprite[heightSprite][widthSprite];

        for (int y = 0; y < heightSprite; y++) {
            for (int x = 0; x < widthSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }

    public void setEffect(Sprite.effect e) {
        SPRITESHEET.setEffect(e);
    }

    public Sprite getSpriteSheet() {
        return SPRITESHEET;
    }

    public Sprite getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * width, y * height, width, height);
    }

    public Sprite getNewSprite(int x, int y) {
        return SPRITESHEET.getNewSubimage(x * width, y * height, width, height);
    }

    public Sprite getSprite(int x, int y, int w, int h) {
        return SPRITESHEET.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage getSubimage(int x, int y, int w, int h) {
        return SPRITESHEET.image.getSubimage(x, y, w, h);
    }

    public Sprite[] getSpriteArray(int i) {
        return spriteArray[i];
    }

    public Sprite[][] getSpriteArray2() {
        return spriteArray;
    }

    public static void drawArray(Graphics2D g, ArrayList<Sprite> img, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        for (int i = 0; i < img.size(); i++) {
            if (img.get(i) != null) {
                g.drawImage(img.get(i).image, (int) x, (int) y, width, height, null);
            }

            x += xOffset;
            y += yOffset;
        }
    }

    public static void drawArray(Graphics2D g, String word, Vector2f pos, int size) {
        drawArray(g, currentFont, word, pos, size, size, size, 0);
    }

    public static void drawArray(Graphics2D g, String word, Vector2f pos, int size, int xOffset) {
        drawArray(g, currentFont, word, pos, size, size, xOffset, 0);
    }

    public static void drawArray(Graphics2D g, String word, Vector2f pos, int width, int height, int xOffset) {
        drawArray(g, currentFont, word, pos, width, height, xOffset, 0);
    }

    public static void drawArray(Graphics2D g, Client.View.Graphics.Font f, String word, Vector2f pos, int size, int xOffset) {
        drawArray(g, f, word, pos, size, size, xOffset, 0);
    }

    public static void drawArray(Graphics2D g, Font f, String word, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        currentFont = f;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != 32)
                g.drawImage(f.getLetter(word.charAt(i)), (int) x, (int) y, width, height, null);

            x += xOffset;
            y += yOffset;
        }

    }

}