package Common.GameBoard;

import Client.Maths.Vector2f;
import Server.Game.ModelClasses.Receptor;
import Common.Receptors.Trap;
import Client.View.Graphics.Sprite;
import Client.View.Graphics.SpriteSheet;

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

    private Receptor occupant;

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
     * Permet de savoir si une case est occupée par une créature.
     * @return true si occupée, false sinon.
     */
    public boolean isEmpty() {
        return occupant == null || occupant.getClass() == Trap.class;
    }

    public void setOccupant(Receptor occupant) {
        this.occupant = occupant;
    }

    public void leave() {
        this.occupant = null;
    }

    public void render(Graphics2D g) {
        g.drawImage(image.image, (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size, null);
    }

    public Receptor getOccupant() {
        return occupant;
    }
}
