package gui.gameWindows;

import gameLogic.receptors.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Génère une fenêtre indiquant la fin de la partie + indique vainqueur / perdant
 */
public class GameOverWindow extends GameWindow {
  private VBox body; // le corps de la fenêtre.
  private Player loser, winner;
  /**
   * Constructeur d'une fenêtre de jeu
   *
   * @param racine : la racine du projet
   * @param navigation : la barre de navigation
   * @param isGaming : indique si les joueurs ont commencé la partie
   * @param stage : le stage du GUI
   */
  public GameOverWindow(BorderPane racine, HBox navigation, boolean isGaming, Stage stage, Player player1, Player player2) {
    super(racine, navigation, isGaming, stage);
  }

  private void generate() {
    Label instructionsTitle = new Label("GAME OVER");
    instructionsTitle.getStyleClass().add("instructions-title");

    //TODO : afficher le gagnant et le vainqueur

    body.getChildren().addAll(instructionsTitle);
    body.setAlignment(Pos.CENTER);
    body.setSpacing(50);

    racine.setCenter(body);
  }
}
