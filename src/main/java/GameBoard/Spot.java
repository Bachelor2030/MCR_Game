package GameBoard;

/**
 * Cette classe représente une case qui constitue une ligne de combat
 */
public class Spot {
    //un case est représentée par un numéro
    private final int number;
    private static int spotCounter = 0;

    /**
     * Constructeur de la classe Spote
     */
    public Spot() {
        this.number = ++spotCounter;
    }
}
