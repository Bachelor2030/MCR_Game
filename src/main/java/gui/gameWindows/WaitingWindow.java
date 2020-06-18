package gui.gameWindows;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/** Représente la fenêtre d'attente de l'adversaire */
public class WaitingWindow extends GameWindow {
  private VBox body; //le corps de la page
  private String imgAttentPath = "src/main/resources/design/images/utils/waitingEye.gif";

  /**
   * Constructeur de la classe
   * @param racine : la racine du jeu
   * @param navigation : la barre de navigation
   * @param stage : le stage permettant d'afficher la GUI
   * @param isGaming : la partie a-t-elle commencé ou non
   * @throws FileNotFoundException
   */
  public WaitingWindow(BorderPane racine, HBox navigation, boolean isGaming, Stage stage)
      throws FileNotFoundException {
    super(racine, navigation, isGaming, stage);
    body = new VBox();
    body.getStyleClass().add("parameters-body");
    body.setAlignment(Pos.CENTER);
    body.setSpacing(50);
    execute();
  }

  /**
   * Permet de générer le corps de la classe
   * @throws FileNotFoundException
   */
  public void execute() throws FileNotFoundException {
    Label title = new Label("Attente de votre adversaire...");
    title.getStyleClass().add("instructions-title");

    Image image = new Image(new FileInputStream(imgAttentPath));
    ImageView imageView = new ImageView(image);

    body.getChildren().addAll(title, imageView);
  }

  public VBox getBody() {
    return body;
  }
}
