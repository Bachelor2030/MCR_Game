package gui.gameWindows;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Permet de créer une fenêtre de jeu
 */
public abstract class GameWindow {
  //La navigation liée au jeu
  protected HBox navigation;

  //La racine liée à la GUI
  protected BorderPane racine;

  //Est-ce que les joueurs sont en pleine partie
  protected boolean isGaming;

  //Le stage servant à afficher la GUI
  protected Stage stage;

  /**
   * Constructeur d'une fenêtre de jeu
   * @param racine : la racine du projet
   * @param navigation : la barre de navigation
   * @param isGaming : indique si les joueurs ont commencé la partie
   * @param stage : le stage du GUI
   */
  public GameWindow(BorderPane racine, HBox navigation, boolean isGaming, Stage stage) {
    this.racine = racine;
    racine.setRight(null);
    racine.setLeft(null);
    racine.setTop(null);
    racine.setBottom(null);
    this.navigation = navigation;
    this.isGaming = isGaming;
    this.stage = stage;
    racine.setTop(this.navigation);
  }
}
