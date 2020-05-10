package GameBoard;

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

    /**
     * Constructeur de la classe Spot.
     */
    public Spot() {
        this.number = ++spotCounter;
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
}
