package gui.receptors;

public class GUICreature extends GUIReceptor {
    private int life, steps, attack;
    private boolean asleep = false;

    public GUICreature() {
        super("", "");
    }

    public GUICreature(String name, int life, int steps, int attack) {
        super(name, "src/main/resources/design/images/creatures/empty.jpg");
        this.life = life;
        this.steps = steps;
        this.attack = attack;
    }

    public GUICreature(String name, String imgPath, int life, int steps, int attack) {
        super(name, imgPath);
        this.life = life;
        this.steps = steps;
        this.attack = attack;
    }

    public int getSteps() {
        return steps;
    }

    public void knockOut() {
        asleep = true;
    }

    public void wakeUp() {
        asleep = false;
    }

    public void setMovementsPoints(int steps) {
        this.steps = steps;
    }

    public int getAttackPoints() {
        return attack;
    }

    public void setAttackPoints(int attack) {
        this.attack = attack;
    }

    public int getLifePoints() {
        return life;
    }

    public void setLifePoints(int life) {
        this.life = life;
    }
}
