package gui;

import gui.buttons.GameButton;
import gui.buttons.UtilButton;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import network.states.ClientSharedState;

import java.util.LinkedList;

import static gui.GameBoard.WIDTH_WINDOW;

public class NavigationBar {
  // les boutons à rajouter.
  private LinkedList<GameButton> buttons;
  private UtilButton minimizeButton;
  private UtilButton quitButton;

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

    /*
        // SEPARATEUR - séparer les utility buttons sur la droite
        // utility buttons : minimize, quit.
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        barreNavigation.getChildren().add(spacer);

        minimizeButton = new UtilButton("header-quit-button", "/design/images/minimize.png");
        minimizeButton
            .getButton()
            .setOnAction(
                event -> {
                  Stage stage = (Stage) minimizeButton.getButton().getScene().getWindow();
                  stage.setIconified(true);
                });

        quitButton = new UtilButton("header-quit-button", "/design/images/quit.png");
        quitButton
            .getButton()
            .setOnAction(
                event -> {
                  if (isGaming) {
                    generateAlert();
                  } else {
                    ((Stage) quitButton.getButton().getScene().getWindow()).close();
                  }
                });

        barreNavigation.getChildren().addAll(minimizeButton.getButton(), quitButton.getButton());
    */
    return barreNavigation;
  }

  /** @return la barre de navigation sous forme de HBox. */
  public HBox getBarreNavigation() {
    return barreNavigation;
  }

  /** Permet de générer une alerte qui demande à l'utilisateur s'il veut bien quitter. */
  private void generateAlert() {
    alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Quitter l'application");
    alert.setHeaderText("");
    alert.setContentText("Vous êtes en pleine partie, voulez-vous réellement quitter le jeu ?");

    // enlève les boutons de base de la fenêtre.
    alert.initStyle(StageStyle.UNDECORATED);
    ButtonType oui = new ButtonType("Quitter");
    ButtonType non = new ButtonType("Annuler");

    alert.getButtonTypes().setAll(oui, non);

    DialogPane dialogPane = alert.getDialogPane();
    dialogPane
        .getStylesheets()
        .add(getClass().getResource("/design/css/styleSheet.css").toExternalForm());
    alert.showAndWait();
    if (alert.getResult() == oui) {
      ((Stage) quitButton.getButton().getScene().getWindow()).close();
    }
  }

  public LinkedList<GameButton> getButtons() {
    return buttons;
  }
}
