package GameLogic.Board;

import GameLogic.Receptors.Receptor;
import GameLogic.Receptors.Trap;

public class Spot {
    private int spotNumber;
    private Receptor occupant;

    public Spot(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public boolean isEmpty() {
        return occupant == null || occupant.getClass() == Trap.class;
    }

    public boolean isTrapped() {
        return occupant != null && occupant.getClass() == Trap.class;
    }

    public Receptor getOccupant() {
        return occupant;
    }

    /**
     * Permet de set l'éventuelle créature présente sur la case.
     *
     * @param occupant : la créature
     */
    public void setOccupant(Receptor occupant) {
        this.occupant = occupant;
    }

    /** Modélise le départ d'une créature de la case. */
    public void leave() {
        this.occupant = null;
    }

}
