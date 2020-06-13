package Server.Game.ModelClasses;

public abstract class Receptor {
    protected String name;

    public Receptor(String name) {
        this.name = name;
    }

    public Receptor() {
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public abstract void playTurn(int turn);

    @Override
    public String toString() {
        return name;
    }
}
