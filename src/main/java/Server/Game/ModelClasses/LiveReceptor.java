package Server.Game.ModelClasses;

import Common.Receptors.Player;
import Server.Game.Position;

public abstract class LiveReceptor extends Receptor {
    protected Position position;       // Position of the creature
    protected final int MAX_LIFE_POINTS;
    protected int lifePoints;
    protected Player owner;
    private final String type;

    public LiveReceptor(String name, int lifePoints, String type) {
        super(name);
        this.lifePoints = lifePoints;
        this.MAX_LIFE_POINTS = lifePoints;
        this.type = type;
    }

    public String getOwnerName() {
        return owner.getName();
    }

    public void loseLifePoints(int points) {
        if (lifePoints - points >= 0) {
            lifePoints -= points;
        } else {
            lifePoints = 0;
        }
    }

    public void gainLifePoints(int points) {
        if(lifePoints + points <= MAX_LIFE_POINTS) {
            lifePoints += points;
        } else {
            lifePoints = MAX_LIFE_POINTS;
        }
    }

    public boolean isAlly(LiveReceptor liveReceptor) {
        return owner.equals(liveReceptor.owner);
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public int getMAX_LIFE_POINTS() {
        return MAX_LIFE_POINTS;
    }

    public boolean isAlive() {
        return lifePoints > 0;
    }

    public String getType() {
        return type;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    /**
     * Places the creature at the given position
     * This puts the creature on the game board
     * @param position the position at which to place the creature on the board
     */
    public void place(Position position) {
        if(position != null) {
            this.position = position;
            this.position.setOccupant(this);
        }
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return super.toString() + " " + lifePoints + "/" + MAX_LIFE_POINTS + " LP";
    }
}
