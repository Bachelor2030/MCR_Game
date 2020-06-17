package gui.receptors;

public class GUICreature extends GUIReceptor {
    private int life, steps, attack;

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

    public int getAttack() {
        return attack;
    }

    public int getLife() {
        return life;
    }

    public int getSteps() {
        return steps;
    }
}
