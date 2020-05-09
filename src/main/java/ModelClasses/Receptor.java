package ModelClasses;

public abstract class Receptor {
    protected int lifePoints;
    protected String name;

    public Receptor(String name, int lifePoints) {
        this.name = name;
        this.lifePoints = lifePoints;
    }

    public void hit(int lifePoints) {
        this.lifePoints -= lifePoints;
    }

    public abstract void playTurn(int turn);

    public String getName() {
        return name;
    }

    public int getLifePoints() {
        return lifePoints;
    }
}
