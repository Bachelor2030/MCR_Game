
import View.GameBoard;

import java.io.*;
import GameBoard.Board;

public class Launcher {
    private static GameBoard gameBoard; //front-end GUI
    private static Board board; //back-end LOGIC

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, I am an awesome game !");
        GameCreator gameCreator = gameCreatorFromFile("src/main/resources/cards.json");

        initGame();


    }

    private static void initGame() throws IOException {
        board = new Board();
        gameBoard = new GameBoard();
        gameBoard.start();
    }

    private static GameCreator gameCreatorFromFile(String fileName) {
        try {
            BufferedReader buf = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileName)
                    )
            );

            String line = buf.readLine(); StringBuilder sb = new StringBuilder();
            while(line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            String fileAsString = sb.toString();
            return new GameCreator(fileAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
