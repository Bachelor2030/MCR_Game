package Client.View.Graphics;

import Client.Maths.Matrix;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Cette classe représente une image à afficher sur le board.
 */
public class Sprite {

    //l'image à afficher
    public BufferedImage image;

    private int[] pixels;
    private int[] ogpixels;

    //La largeur du sprite
    private int width;

    //la hauteur du sprite
    private int height;

    public static enum effect {NORMAL, SEPIA, REDISH, GRAYSCALE, NEGATIVE, DECAY};

    private float[][] id =
            {{1.0f, 0.0f, 0.0f},
            {0.0f, 1.0f, 0.0f},
            {0.0f, 0.0f, 1.0f},
            {0.0f, 0.0f, 0.0f}};

    private float[][] negative =
            {{1.0f, 0.0f, 0.0f},
            {0.0f, 1.0f, 0.0f},
            {0.0f, 0.0f, 1.0f},
            {0.0f, 0.0f, 0.0f}};

    private float[][] decay =
            {{0.000f, 0.333f, 0.333f},
            {0.333f, 0.000f, 0.333f},
            {0.333f, 0.333f, 0.000f},
            {0.000f, 0.000f, 0.000f}};

    private float[][] sepia =
            {{0.393f, 0.349f, 0.272f},
            {0.769f, 0.686f, 0.534f},
            {0.189f, 0.168f, 0.131f},
            {0.000f, 0.000f, 0.000f}};

    private float[][] redish =
            {{1.0f, 0.0f, 0.0f},
            {0.0f, 0.3f, 0.0f},
            {0.0f, 0.0f, 0.3f},
            {0.0f, 0.0f, 0.0f}};

    private float[][] grayscale =
            {{0.333f, 0.333f, 0.333f},
            {0.333f, 0.333f, 0.333f},
            {0.333f, 0.333f, 0.333f},
            {0.000f, 0.000f, 0.000f}};

    private float[][] currentEffect = id;

    /**
     * Constructeur de la classe Sprite
     * @param image : l'image du sprite
     */
    public Sprite(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        ogpixels = image.getRGB(0, 0, width, height, ogpixels, 0, width);
        pixels = ogpixels;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void saveColors() {
        pixels = image.getRGB(0, 0, width, height, pixels, 0, width);
        currentEffect = id;
    }

    public void restoreColors() {
        image.setRGB(0, 0, width, height, pixels, 0, width);
    }

    public void restoreDefault() {
        image.setRGB(0, 0, width, height, ogpixels, 0, width);
    }

    //en format #FFFFFF
    public Color hexToColor(String color) {
        return new Color(
                Integer.valueOf(color.substring(1, 3), 16),
                Integer.valueOf(color.substring(3, 5), 16),
                Integer.valueOf(color.substring(5, 7), 16));
    }

    public void setContrast(float value) {
        float[][] effect = id;
        float contrast = (259 * (value + 255)) / (255 * (259 - value));
        for(int i = 0; i < 3; i++) {
            if(i < 3)
                effect[i][i] = contrast;
            effect[3][i] = 128 * (1 - contrast);
        }

        addEffect(effect);
    }

    /**
     * Permet de gérer la luminosité
     * @param value : la valeur de la luminosité
     */
    public void setBrightness(float value) {
        float[][] effect = id;
        for(int i = 0; i < 3; i++)
            effect[3][i] = value;

        addEffect(effect);
    }

    /**
     * Permet de réaliser un effet sur le sprite.
     * Par exemple, si la créature meurt, on peut la mettre en sepia.
     * @param e : l'effet à réaliser
     */
    public void setEffect(effect e) {
        float[][] effect;
        switch (e) {
            case SEPIA: effect = sepia;
                break;
            case REDISH: effect = redish;
                break;
            case GRAYSCALE: effect = grayscale;
                break;
            case NEGATIVE: effect = negative;
                break;
            case DECAY: effect = decay;
                break;
            default: effect = id;
        }

        if(effect != currentEffect) {
            addEffect(effect);
        }
    }

    private void addEffect(float[][] effect) {
        float[][] rgb = new float[1][4];
        float[][] xrgb;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                int p = pixels[x + y * width];

                int a = (p >> 24) & 0xff;

                rgb[0][0] = (p >> 16) & 0xff;
                rgb[0][1] = (p >> 8) & 0xff;
                rgb[0][2] = (p) & 0xff;
                rgb[0][3] = 1f;

                xrgb = Matrix.multiply(rgb, effect);

                for(int i = 0; i < 3; i++) {
                    if(xrgb[0][i] > 255) rgb[0][i] = 255;
                    else if(xrgb[0][i] < 0) rgb[0][i] = 0;
                    else rgb[0][i] = xrgb[0][i];
                }

                p = (a<<24) | ((int) rgb[0][0]<<16) | ((int) rgb[0][1]<<8) | (int) rgb[0][2];
                image.setRGB(x, y, p);
            }
        }
        currentEffect = effect;
    }

    /**
     * Sert à l'animation
     * @param x : coordonnée en haut à gauche
     * @param y : coordonnée en haut à gauche
     * @param w : largeur de la région
     * @param h : hauteur de la région
     * @return un sprite avec les dimensions souhaitées
     */
    public Sprite getSubimage(int x, int y, int w, int h) {
        return new Sprite(image.getSubimage(x, y, w, h));
    }

    /**
     * Sert à l'animation
     * @param x : coordonnée en haut à gauche
     * @param y : coordonnée en haut à gauche
     * @param w : largeur de la région
     * @param h : hauteur de la région
     * @return un sprite avec les dimensions souhaitées
     */
    public Sprite getNewSubimage(int x, int y, int w, int h) {
        BufferedImage temp = image.getSubimage(x, y, w, h);
        BufferedImage newImage = new BufferedImage(image.getColorModel(), image.getRaster().createCompatibleWritableRaster(w,h), image.isAlphaPremultiplied(), null);
        temp.copyData(newImage.getRaster());
        return new Sprite(newImage);
    }

    public Sprite getNewSubimage() {
        return getNewSubimage(0, 0, this.width, this.height);
    }
}

