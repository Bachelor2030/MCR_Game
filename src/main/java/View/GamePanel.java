package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Représente le panel intérieur pour afficher les objets du jeu
 */
public class GamePanel extends JPanel {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    public GamePanel(){
        super();
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setVisible(true);
        this.setBackground(Color.BLACK); //fond noir
    }
}

