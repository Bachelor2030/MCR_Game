package gui.receptors;

/**
 * Représentation d'une créature pour la GUI
 */
public class GUICreature extends GUIReceptor
{
  private int life, steps, attack; //points, déplacement, pts d'attaque

  /**
   * Constructeur de la classe
   */
  public GUICreature() {
    super("", "src/main/resources/design/images/creatures/empty.jpg");
  }

  /**
   * Constructeur de la classe
   * @param name : nom de la créature
   * @param imgPath : image de la créature
   * @param life : points de vie de la créature
   * @param steps : déplacement de la créature
   * @param attack : points d'attaque de la créature
   */
  public GUICreature(String name, String imgPath, int life, int steps, int attack) {
    super(name, imgPath);
    this.life = life;
    this.steps = steps;
    this.attack = attack;
  }

  /**
   * @return points de déplacement de la créature.
   */
  public int getSteps() {
    return steps;
  }

  /**
   * Permet d'assomer une créature.
   */
  public void knockOut() {
  }

  /**
   * Permet de réveiller une créature assomée.
   */
  public void wakeUp() {
  }

  /**
   * Permet de définir les points de déplacement de la créature.
   * @param steps : le nombre de pas
   */
  public void setMovementsPoints(int steps) {
    this.steps = steps;
  }

  /**
   * @return le nombre de points d'attaque
   */
  public int getAttackPoints() {
    return attack;
  }

  /**
   * Permet de redéfinir les points d'attaque
   * @param attack : le nombre de points d'attaque.
   */
  public void setAttackPoints(int attack) {
    this.attack = attack;
  }

  /**
   * @return les points de vie
   */
  public int getLifePoints() {
    return life;
  }

  /**
   * Permet de redéfinir les points de vie
   * @param life : les points de vie
   */
  public void setLifePoints(int life) {
    this.life = life;
  }

}
