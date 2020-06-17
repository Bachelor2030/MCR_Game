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
  private VBox body;
  private String imgAttentPath = "src/main/resources/design/images/utils/waitingEye.gif";

  public WaitingWindow(BorderPane racine, HBox navigation, boolean isGaming, Stage stage)
      throws FileNotFoundException {
    super(racine, navigation, isGaming, stage);
    body = new VBox();
    body.getStyleClass().add("parameters-body");
    body.setAlignment(Pos.CENTER);
    body.setSpacing(50);
    execute();
  }

  public void execute() throws FileNotFoundException {
    Label title = new Label("Attente de votre adversaire...");
    title.getStyleClass().add("instructions-title");

    Image image = new Image(new FileInputStream(imgAttentPath));
    ImageView imageView = new ImageView(image);
    // imageView.setFitWidth(image.getWidth() * 0.5);
    // imageView.setFitHeight(image.getHeight() * 0.5);

    body.getChildren().addAll(title, imageView);
  }

  public VBox getBody() {
    return body;
  }
}
