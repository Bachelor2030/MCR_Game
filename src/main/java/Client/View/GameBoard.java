package Client.View;

import Common.GameBoard.Board;
import Common.GameBoard.Spot;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/** Permet de représenter l'entierté du jeu */
public class GameBoard extends Application {
  private static Board board;
  private static GameBoard gameBoard;

  public GameBoard() throws IOException {
    board = new Board();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Spot spot = new Spot();
    primaryStage = new Stage();
    Image img = new Image(spot.getImage());
    ImageView imageView = new ImageView(img);

    HBox hbox = new HBox(imageView);

    Scene scene = new Scene(hbox, 1100, 700, Color.BLACK);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
