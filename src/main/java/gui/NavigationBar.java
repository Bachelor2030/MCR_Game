package gui;

import gui.buttons.GameButton;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import network.states.ClientSharedState;

import java.util.LinkedList;

import static gui.GameBoard.WIDTH_WINDOW;

public class NavigationBar {
  // les boutons à rajouter.
  private LinkedList<GameButton> buttons;

  // la barre de navigation.
  private HBox barreNavigation;

  // le style de la box.
  private String styleClass;

  // permet de savoir si l'user est en partie ou non.
  private boolean isGaming;

  // fenêtre d'alert.
  private Alert alert;

  private ClientSharedState clientSharedState;

  /**
   * @param buttons : les boutons à mettre dans la barre.
   * @param styleClass : le style de la barre.
   * @param isGaming : est-ce que l'user est en partie ou non.
   */
  public NavigationBar(LinkedList<GameButton> buttons, String styleClass, boolean isGaming) {
    this.buttons = buttons;
    this.styleClass = styleClass;
    this.isGaming = isGaming;
  }

  /**
   * Permet de générer la barre de navigation avec tous les boutons nécessaires.
   *
   * @return la barre de navigation sous forme de HBox.
   */
  protected HBox generate() {
    barreNavigation = new HBox(10);
    if (!buttons.isEmpty()) {
      for (GameButton b : buttons) {
        barreNavigation.getChildren().add(b.getButton());
      }
    }

    // on règle l'écart du contenu intérieur avec les bords de la boxe
    barreNavigation.setPadding(new Insets(15, 15, 15, 15));
    barreNavigation.setPrefWidth(WIDTH_WINDOW);

    // Espace entre les éléments
    barreNavigation.setSpacing(10);

    // On lui applique d'autres styles présents dans la feuille CSS
    barreNavigation.getStyleClass().add(styleClass);

    return barreNavigation;
  }

  /** @return la barre de navigation sous forme de HBox. */
  public HBox getBarreNavigation() {
    return barreNavigation;
  }
}
