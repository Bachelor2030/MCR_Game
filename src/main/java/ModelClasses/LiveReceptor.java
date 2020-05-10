package ModelClasses;

public abstract class LiveReceptor extends Receptor {
    protected int lifePoints;

    public LiveReceptor(String name, int lifePoints) {
        super(name);
        this.lifePoints = lifePoints;
    }

    public void hit(int points) {
        lifePoints -= points;
    }

    public void heal(int points) {
        lifePoints += points;
    }

    public int getLifePoints() {
        return lifePoints;
    }
}
