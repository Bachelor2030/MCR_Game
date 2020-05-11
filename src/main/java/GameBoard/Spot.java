package GameBoard;
import Maths.Vector2f;
import View.Graphics.Sprite;
import View.Graphics.SpriteSheet;

import java.awt.*;
import java.io.IOException;

/**
 * Cette classe représente une case qui constitue une ligne de combat
 */
public class Spot {
    //un case est représentée par un numéro
    private final int number;

    //Compteur de case
    private static int spotCounter = 0;

    //Est-ce que la case est piégée ou non.
    private boolean isTrapped = false;

    //Est-ce qu'une créature est placée sur la case ou non.
    private boolean isBusy = false;

    protected SpriteSheet sprite;
    protected Sprite image;
    protected Vector2f pos;
    protected int size;
    protected int spriteX;
    protected int spriteY;

    /**
     * Constructeur de la classe Spot.
     */
    public Spot() throws IOException {
        this(++spotCounter, new Vector2f(), 0);
    }

    public Spot(SpriteSheet sprite, Vector2f pos, int size) {
        this(++spotCounter, pos, size);
        this.sprite = sprite;
    }

    public Spot(int number, Vector2f pos, int size)
    {
        this.number = number;
        this.pos = pos;
        this.size = size;
    }

    /**
     * Permet de savoir si une case est piégée ou non.
     * @return true si piégée, false sinon.
     */
    public boolean isTrapped() {
        return isTrapped;
    }

    /**
     * Permet d'indiquer qu'une case est piégée ou non.
     * @param trapped : true si piégée, false sinon.
     */
    public void setTrapped(boolean trapped) {
        isTrapped = trapped;
    }

    /**
     * Permet de savoir si une case est occupée par une créature.
     * @return true si occupée, false sinon.
     */
    public boolean isBusy() {
        return isBusy;
    }

    /**
     * Permet de définir si une case est occupée par une créature.
     * @param busy : true si occupée, false sinon.
     */
    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void render(Graphics2D g) {
        g.drawImage(image.image, (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }
}
