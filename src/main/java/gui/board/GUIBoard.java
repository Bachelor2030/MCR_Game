package gui.board;

import gameLogic.receptors.Player;
import gameLogic.receptors.Receptor;
import gui.receptors.GUIPlayer;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Cette classe représente le "plateau" où se déplacent les différentes créatures lors d'une partie.
 * Une plateau comporte 4 lignes.
 */
public class GUIBoard {

    //TODO : rentre paramétrique.
    //le nombre de lignes
    private final int NB_LINES = 4;

    //compteur de ligne
    private static int lineCounter;

    //les lignes du board
    private LinkedList<GUILine> GUILines;

    Group root;

    /**
     * Constructeur de la classe GUIBoard
     */
    public GUIBoard(GridPane gridPane, VBox vbox, GUIPlayer player1, GUIPlayer player2) throws IOException {
        lineCounter = 0;
        GUILines = new LinkedList<>();
        for(int line = 0; line < NB_LINES; ++line) {
            GUILines.add(new GUILine(lineCounter++, gridPane, vbox, player1, player2, player1.getClientSharedState()) );
        }
    }

    public void place(Receptor receptor, int lineCounter, int spot) {
        GUILines.get(lineCounter).setReceptor(receptor, spot);
    }

    public GUILine getLine(int index) {
        if (index < GUILines.size()) {
            return GUILines.get(index);
        }
        return null;
    }

    /**
     * Permet de récupérer la liste des lignes du board.
     * @return la liste des lignes du board.
     */
    public LinkedList<GUILine> getGUILines() {
        return GUILines;
    }

    /**
     * Permet de savoir le nombre de lignes constituant un board.
     * @return le nombre de lignes constituant un board.
     */
    public int getNB_LINES() {
        return NB_LINES;
    }
}
