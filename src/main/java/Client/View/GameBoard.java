package Client.View;

import Common.GameBoard.Board;
import Common.GameBoard.Line;
import Common.GameBoard.Spot;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

/** Permet de représenter l'entierté du jeu */
public class GameBoard extends Application {
  private static Board board;
  private LinkedList<Line> lines;
  private Group root;
  private Stage primaryStage;
  private Scene scene;

  public GameBoard() throws IOException {
    root = new Group();
    board = new Board(root);
    primaryStage = new Stage();
    initGame();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    scene = new Scene(root, Color.BLACK);
    primaryStage.setFullScreen(true);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Permet d'initialiser correctement les différentes structures du board.
   * @throws IOException
   */
  private void initGame() throws IOException {

  }
}
