package gui.gameWindows;

import gameLogic.Receptors.Player;
import gui.board.GUIBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InGameWindow extends GameWindow {
  private GridPane gridIslandsPanel;
  private GUIBoard GUIBoard;
  private Player player1, player2;

  public InGameWindow(
      BorderPane racine,
      HBox navigation,
      GridPane gridIslandsPanel,
      GUIBoard GUIBoard,
      Player player1,
      Player player2,
      boolean isGaming,
      Stage stage)
      throws IOException {
    super(racine, navigation, isGaming, stage);
    this.gridIslandsPanel = gridIslandsPanel;
    this.GUIBoard = GUIBoard;
    this.player1 = player1;
    this.player2 = player2;
    this.generateBody();
  }

  private void generateBody() throws IOException {

    // On met en place le corps du texte
    racine.setCenter(displayInGameField());

    // On créé l'espace d'infos du joueur 1
    racine.setLeft(getPlayerInformations("Player 1"));

    // On créé l'espace d'infos du joueur 1
    racine.setRight(getPlayerInformations("Player 2"));

    // On crée un footer dans le BorderPane
    racine.setBottom(footerBar());
  }

  /**
   * Affiche le terrain où se déplace les créatures.
   *
   * @return le gridPane sur lequel se déplace les créatures.
   * @throws IOException
   */
  private GridPane displayInGameField() throws IOException {
    VBox corpsInstruction = new VBox(); // contient les lignes du board.
    corpsInstruction.getStyleClass().add("instructions-body");
    gridIslandsPanel = new GridPane(); // représente le board du jeu.
    gridIslandsPanel.getStyleClass().add("corps-gridPane");
    VBox vbox = new VBox(); // contient une créature et un emplacement.

    /*
    int numRows = 5;
    for(int i = 0;i < numRows; i++)
    {
      RowConstraints rc = new RowConstraints();
      rc.setPercentHeight(100 / numRows);
      gridIslandsPanel.getRowConstraints().add(rc);
    }
    */

    // Répertoire contenant nos îles
    GUIBoard = new GUIBoard(gridIslandsPanel, vbox, player1, player2);
    gridIslandsPanel.setAlignment(Pos.CENTER);
    return gridIslandsPanel;
  }

  /** @return les informations du player */
  private VBox getPlayerInformations(String labelTitle) {
    VBox informationPannelUser = new VBox();

    // On l'affiche
    informationPannelUser.getStyleClass().add("menuLabelsGauche-vbox");

    // On créé le titre "Actions"
    Label informationPannelUserTitle = new Label("Joueur 1");
    informationPannelUserTitle.getStyleClass().add("titre-label");

    informationPannelUser.getChildren().add(informationPannelUserTitle);
    return informationPannelUser;
  }
  /**
   * Footer Il s'agit de la fonction initialisant tout ce qui est inhérent au footer. Dans ce
   * footer, nous affichons les cartes du joueur.
   *
   * @return le footer
   */
  private HBox footerBar() throws FileNotFoundException {

    // On définit une boxe horizontale qui définira l'espace "footer" -> cartes du joueur
    HBox footerCardsPlayer = new HBox();
    footerCardsPlayer.setPadding(new Insets(15, 15, 15, 15));
    footerCardsPlayer.getStyleClass().add("footer-header-hbox");

    // À REFACTORER SA MERE DANS UNE AUTRE CLASSE -----------
    for (int i = 0; i < 5; i++) {
      FileInputStream imagePath =
          new FileInputStream("src/main/resources/design/images/cards/cardSample.png");
      Image image = new Image(imagePath);
      ImageView imageView = new ImageView(image);
      imageView.setFitWidth(image.getWidth() * 0.7);
      imageView.setFitHeight(image.getHeight() * 0.7);
      // -------------------------------------
      footerCardsPlayer.getChildren().add(imageView);
      footerCardsPlayer.getStyleClass().add("corps-gridPane");
    }

    return footerCardsPlayer;
  }
}
