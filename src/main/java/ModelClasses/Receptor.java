package ModelClasses;

public abstract class Receptor {
    protected int lifePoints;
    protected String name;

    public Receptor(String name, int lifePoints) {
        this.name = name;
        this.lifePoints = lifePoints;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public String getName() {
        return name;
    }

    public abstract void action();
    public abstract void playTurn();
}
