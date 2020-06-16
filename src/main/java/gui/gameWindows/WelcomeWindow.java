package gui.gameWindows;

import gameLogic.receptors.Player;
import gui.buttons.GameButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WelcomeWindow extends GameWindow {
  private int WIDTH_WINDOW;
  private Stage stage;

  public WelcomeWindow(
      BorderPane racine, HBox navigation, Stage stage, final int WIDTH_WINDOW, Player player) {
    super(racine, navigation, false, stage);
    this.stage = stage;
    this.WIDTH_WINDOW = WIDTH_WINDOW;
  }

  private void generate() throws FileNotFoundException {
    VBox principalMenu = new VBox();
    VBox buttons = new VBox();

    // BACHELOR HUNTERZ TITRE
    FileInputStream imagePath = new FileInputStream("src/main/resources/design/images/bh.png");
    ImageView imageMenuView = new ImageView();
    Image imageMenu = new Image(imagePath);
    imageMenuView.setImage(imageMenu);

    // INSTRUCTIONS BUTTON
    GameButton instructionButton = new GameButton("Instructions", "bouton-menu-principal");
    instructionButton
        .getButton()
        .setOnAction(
            actionEvent -> {
              InstructionWindow instructionWindow =
                  new InstructionWindow(racine, navigation, false, stage);
            });

    // NEW GAME BUTTON
    GameButton newGameButton = new GameButton("Nouvelle partie", "bouton-menu-principal");
    newGameButton
        .getButton()
        .setOnAction(
            actionEvent -> {
              ParameterWindow parameterWindow =
                  new ParameterWindow(racine, navigation, stage, isGaming);
            });

    buttons.getChildren().addAll(instructionButton.getButton(), newGameButton.getButton());
    buttons.setSpacing(25);
    buttons.setAlignment(Pos.CENTER);

    // on règle l'écart du contenu intérieur avec les bords de la boxe
    principalMenu.setPadding(new Insets(15, 15, 15, 15));
    principalMenu.setPrefWidth(WIDTH_WINDOW);

    // Espace entre les éléments
    principalMenu.setSpacing(100);

    // On lui applique d'autres styles présents dans la feuille CSS
    principalMenu.getStyleClass().add("header-hbox");

    // on ajoute les éléments à la boxe
    principalMenu.getChildren().addAll(imageMenuView, buttons);
    principalMenu.setAlignment(Pos.CENTER);

    // On met en place le corps de la fenêtre
    racine.setCenter(principalMenu);
  }
}
