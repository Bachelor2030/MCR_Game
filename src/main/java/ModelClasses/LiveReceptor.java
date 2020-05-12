package ModelClasses;

public abstract class LiveReceptor extends Receptor {
    protected final int MAX_LIFE_POINTS;
    protected int lifePoints;

    public LiveReceptor(String name, int lifePoints) {
        super(name);
        this.lifePoints = lifePoints;
        this.MAX_LIFE_POINTS = lifePoints;
    }

    public void hit(int points) {
        if (lifePoints - points >= 0) {
            lifePoints -= points;
        } else {
            lifePoints = 0;
        }
    }

    public void heal(int points) {
        if(lifePoints + points <= MAX_LIFE_POINTS) {
            lifePoints += points;
        } else {
            lifePoints = MAX_LIFE_POINTS;
        }
    }

    public int getLifePoints() {
        return lifePoints;
    }

    @Override
    public String toString() {
        return super.toString() + " " + lifePoints + "/" + MAX_LIFE_POINTS + " LP";
    }
}
