package gui.receptors;

/**
 * Modélise un piège pour la GUI
 */
public class GUITrap extends GUIReceptor {
  private String description; //la description du trap

  /**
   * Constructeur de la classe
   * @param description : la description du trap
   */
  public GUITrap(String description) {
    super("", "");
    this.description = description;
  }

}
