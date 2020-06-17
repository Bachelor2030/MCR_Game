package gui.gameWindows;

import gameLogic.receptors.Player;
import gui.GUICard;
import gui.board.GUIBoard;
import gui.receptors.GUIPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class InGameWindow extends GameWindow {
  private GridPane gridIslandsPanel;
  private GUIBoard GUIBoard;
  private GUIPlayer player1, player2;
  ArrayList<GUICard> handPlayer;

  public InGameWindow(
      BorderPane racine,
      HBox navigation,
      GridPane gridIslandsPanel,
      GUIBoard GUIBoard,
      GUIPlayer player1,
      GUIPlayer player2,
      boolean isGaming,
      Stage stage,
      ArrayList<GUICard> handPlayer)
      throws IOException {
    super(racine, navigation, isGaming, stage);
    this.gridIslandsPanel = gridIslandsPanel;
    this.GUIBoard = GUIBoard;
    this.player1 = player1;
    this.player2 = player2;
    this.handPlayer = handPlayer;
    generateBody();
  }

  private void generateBody() throws IOException {

    // On met en place le corps du texte
    racine.setCenter(displayInGameField());

    // On créé l'espace d'infos du joueur 1
    racine.setLeft(getPlayerInformations(player1.getName()));

    // On créé l'espace d'infos du joueur 1
    racine.setRight(getPlayerInformations(player2.getName()));

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

    int numRows = 4;
    for (int i = 0; i < numRows; ++i) {
      RowConstraints rc = new RowConstraints();
      rc.setPercentHeight(100 / numRows);
      gridIslandsPanel.getRowConstraints().add(rc);
    }

    // Répertoire contenant nos îles
    GUIBoard = new GUIBoard(gridIslandsPanel, vbox, player1, player2);
    gridIslandsPanel.setAlignment(Pos.CENTER);
    return gridIslandsPanel;
  }

  /** @return les informations du Player */
  private VBox getPlayerInformations(String labelTitle) throws FileNotFoundException {
    VBox informationPannelUser = new VBox();

    // on set l'image de player 1
    if (labelTitle.equals(player1.getName())) {
      ImageView imageView = new ImageView(player1.getImage());
      imageView.setFitWidth(player1.getImage().getWidth() / 2.5);
      imageView.setFitHeight(player1.getImage().getHeight() / 2.5);
      informationPannelUser.getChildren().add(imageView);
    }

    // on set l'image de player 2 en chequant si elle est pas égale à celle de base.
    // TODO remplace image 2 en reprenant info serveur
    else if (player2.getImage() != null) {
      ImageView imageView = new ImageView(player2.getImage());
      imageView.setFitWidth(player1.getImage().getWidth() / 2.5);
      imageView.setFitHeight(player1.getImage().getHeight() / 2.5);
      informationPannelUser.getChildren().add(imageView);
    }

    // On l'affiche
    informationPannelUser.getStyleClass().add("menuLabelsGauche-vbox");
    informationPannelUser.setAlignment(Pos.CENTER);

    // On créé le titre "Actions"
    Label informationPanelUserTitle = new Label(labelTitle);
    informationPanelUserTitle.getStyleClass().add("titre-label");

    informationPannelUser.getChildren().addAll(informationPanelUserTitle);
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

    for (GUICard card : handPlayer) {
      footerCardsPlayer.getChildren().add(card.getButton());
    }

    footerCardsPlayer.setVisible(true);
    footerCardsPlayer.getStyleClass().add("corps-gridPane");

    return footerCardsPlayer;
  }
}
