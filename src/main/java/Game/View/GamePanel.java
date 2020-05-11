package Game.View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import GameBoard.Spot;
import Maths.Vector2f;
import View.Graphics.Sprite;
import View.Graphics.SpriteSheet;

/**
 * Représente le panel intérieur pour afficher les objets du jeu
 */
public class GamePanel extends JPanel {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    private Spot spot;

    /**
     * Constructeur de la classe GamePanel
     */
    public GamePanel(){
        super();
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setVisible(true);
        this.setBackground(Color.BLACK); //fond noir
    }

    /**
     * Place les différents graphismes du jeu à leur place.
     */
    private void initPanel()
    {
        //spot = new Spot(new SpriteSheet("src/main/java/View/linkformatted.png", 64, 64), new Vector2f(0 + (GamePanel.DEFAULT_WIDTH / 2) - 32, 0 + (GamePanel.DEFAULT_HEIGHT / 2) - 32), 64);

    }
}

