package gui.buttons;

import javafx.scene.control.Button;

/** Cette classe sert à modéliser des boutons présents dans l'interface graphique du jeu. */
public class GameButton {
  // Le bouton associé au GameButton
  private Button button;

  public GameButton(String name, String styleClass) {
    button = new Button(name);
    button.getStyleClass().add(styleClass); // le style de classe appliqué au GameButton
  }

  /**
   * Permet de récupérer le button d'un GameButton.
   *
   * @return le bouton d'un GameButton.
   */
  public Button getButton() {
    return button;
  }
}
