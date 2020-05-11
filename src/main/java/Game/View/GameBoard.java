package Game.View;

import javax.swing.*;
import Game.GameBoard.Board;

import java.io.IOException;

public class GameBoard extends JFrame {
    private GamePanel gamePanel;
    private static Board board;
    private static GameBoard gameBoard;

    public GameBoard() throws IOException {
        super("TITRE DU JEU - PROJET DE MCR");
        board = new Board();
        gamePanel = new GamePanel();
        start();

    }

    public void start() {
        setVisible(true);
        builGUI();
    }

    private void builGUI() {
        JPanel masterPanel = new JPanel();
        masterPanel.add(gamePanel);

        add(gamePanel);
        //add(masterPanel);

        //redimentionne le panel pour que les objets puissent fit.
        pack();
    }

    public static void main(String[] args) throws IOException {
        gameBoard = new GameBoard();
        gameBoard.start();

    }
}
