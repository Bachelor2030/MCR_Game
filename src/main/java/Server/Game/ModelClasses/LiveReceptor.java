package Server.Game.ModelClasses;

import Common.Receptors.Player;

public abstract class LiveReceptor extends Receptor {
    protected final int MAX_LIFE_POINTS;
    protected int lifePoints;
    protected final Player owner;
    private final String type;

    public LiveReceptor(String name, int lifePoints, Player owner, String type) {
        super(name);
        this.lifePoints = lifePoints;
        this.MAX_LIFE_POINTS = lifePoints;
        this.owner = owner;
        this.type = type;
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

    @Override
    public String toString() {
        return super.toString() + " " + lifePoints + "/" + MAX_LIFE_POINTS + " LP";
    }
}
