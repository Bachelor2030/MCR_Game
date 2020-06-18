package gui.receptors;

import javafx.scene.control.ProgressBar;

public class GUICreature extends GUIReceptor
{
  private int life, steps, attack;
  private boolean asleep = false;
  private ProgressBar progressBar;
  private int initialLife;

  public GUICreature() {
    super("", "src/main/resources/design/images/creatures/empty.jpg");
  }

  public GUICreature(String name, int life, int steps, int attack) {
    super(name, "src/main/resources/design/images/creatures/empty.jpg");
    this.life = life;
    initialLife = life;
    this.steps = steps;
    this.attack = attack;
    progressBar = new ProgressBar(life);
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

  public ProgressBar getProgressBar() {
    return progressBar;
  }

  /**
   * Permet d'enlever des points à la barre de vie.
   * @param malus : le nombre de points à enlever.
   */
  public void decreaseProgressBar(int malus) {
    progressBar.setProgress(life-malus);
  }

  /**
   * Permet d'ajouter des points à la barre de vie.
   * @param bonus : le nombre de points à ajouter.
   */
  public void increaseProgressBar(int bonus) {
    if(life + bonus > initialLife) {
      progressBar.setProgress(initialLife);
    }
    else {
      progressBar.setProgress(life + bonus);
    }
  }

}
