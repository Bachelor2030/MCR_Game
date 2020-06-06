import Client.View.GameBoard;
import Common.GameBoard.Board;
import Server.Game.ModelClasses.GameClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Launcher {
  private static GameBoard gameBoard; // front-end GUI
  private static Board board; // back-end LOGIC

  public static void main(String[] args) throws IOException {
    System.out.println("Hello, I am an awesome game !");
    GameClient gameCreator = gameCreatorFromFile("src/main/resources/cards.json");

    initGame();
  }

  private static void initGame() throws IOException {
    board = new Board();
    gameBoard = new GameBoard();
  }

  private static GameClient gameCreatorFromFile(String fileName) {
    try {
      BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

      String line = buf.readLine();
      StringBuilder sb = new StringBuilder();
      while (line != null) {
        sb.append(line).append("\n");
        line = buf.readLine();
      }
      String fileAsString = sb.toString();
      return new GameClient(fileAsString);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
