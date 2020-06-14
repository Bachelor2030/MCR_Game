package GUI;

import GUI.Board.GUIBoard;
import GameLogic.Commands.GuiCommands.EndGame;
import GameLogic.Commands.PlayersAction.EndTurn;
import GameLogic.Invocator.Card.Card;
import GameLogic.Receptors.Player;
import GameLogic.Receptors.Receptor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

// TODO : commande qui font des actions graphiques. (genre déplacer créature)

/** Permet de représenter l'entierté du jeu */
public class GameBoard extends Application {
  // ******************************************************************
  // ATTRIBUTS DE LA CLASSE
  // ******************************************************************

  // Taille fenêtre
  public static final int WIDTH_WINDOW = 1200;
  public static final int HEIGHT_WINDOW = 800;

  // Cors du jeu -> là où se trouvent les îles + créatures & shit
  private GridPane gridIslandsPanel;

  // Affiche les actions joueurs par le player 1
  private ListView<String> actionPlayer1Labels;
  private Label deleteActionLabelP1;

  // Affiche les actions joueurs par le player 1
  private ListView<String> actionPlayer2Labels;
  private Label deleteActionLabelP2;

  private static boolean isGaming;

  private BorderPane racine;

  private Player player1, player2;
  private LinkedList<Card> deck1, deck2;
  private String namePlayer1, namePlayer2, IpPlayer1, IpPlayer2, portPlayer1, portPlayer2;

  private GUIBoard GUIBoard;

  private Stage currentStage;

  /** Thread principal du GUI. Gère l'affichage général de la "scene". */
  @Override
  public void start(Stage stage) throws Exception {
    currentStage = stage;
    // On initialise les players
    player1 = new Player();
    player2 = new Player();

    // Racine de scene
    racine = new BorderPane();

    // fond de base
    racine.getStyleClass().add("general-borderPanel");

    // Affichage du menu principal
    inMainMenu();

    // ------------------------------------------------------------------
    // PARAMÈTRES DE LA FENÊTRE DU LOGICIEL
    // ------------------------------------------------------------------

    Scene scene = new Scene(racine, WIDTH_WINDOW, HEIGHT_WINDOW);

    // on définit le curseur comme croix
    scene.setCursor(Cursor.CROSSHAIR);

    // on applique les styles de la feuille CSS
    scene.getStylesheets().add("/design/css/styleSheet.css");

    stage.setScene(scene);
    // met la fenêtre au max
    stage.setMaximized(true);
    stage.setTitle("MCR - JEU DE LA MUERTA");
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.show();
  }

  public GUIBoard getGUIBoard() {
    return GUIBoard;
  }

  public void place(Receptor receptor, int line, int position) {
    GUIBoard.place(receptor, line, position);
    GUIBoard.getLine(line).getSpot(position).setOccupant(receptor);
  }

  public void exitGame() {
    // TODO afficher la fenêtre de fin
    isGaming = false;
    try {
      inMainMenu();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * MENU PRINCIPAL DU JEU
   *
   * @throws IOException
   */
  private void inMainMenu() throws IOException {
    racine.setRight(null);
    racine.setLeft(null);
    racine.setTop(null);
    racine.setBottom(null);

    isGaming = false;

    // ------------------------------------------------------------------
    // BARRE DE NAVIGATION DU MAIN MENU
    // ------------------------------------------------------------------

    HBox barreNavigation = defineHeader(false);

    // ------------------------------------------------------------------
    // CORPS MAIN MENU
    // ------------------------------------------------------------------

    // BOUTONS INSTRUCTIONS ET NOUVELLE PARTIE
    // On créé une boxe verticale qui définira les deux boutons présents dans le menu.
    VBox principalMenu = new VBox();
    VBox buttons = new VBox();

    // BACHELOR HUNTERZ TITRE
    FileInputStream imagePath = new FileInputStream("src/main/resources/design/images/bh.png");
    ImageView imageMenuView = new ImageView();
    Image imageMenu = new Image(imagePath);
    imageMenuView.setImage(imageMenu);

    // INSTRUCTIONS BUTTON
    GameButton instructionButton = new GameButton("Instructions", "bouton-menu-principal");
    instructionButton.getButton().setOnAction(
        actionEvent -> {
          try {
            displayInstructions();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

    // NEW GAME BUTTON
    GameButton newGameButton = new GameButton("Nouvelle partie", "bouton-menu-principal");
    newGameButton.getButton().setOnAction(
        actionEvent -> {
          try {
            displaySettingsMenu();
          } catch (UnknownHostException e) {
            e.printStackTrace();
          }
        });

    buttons.getChildren().addAll(instructionButton.getButton(), newGameButton.getButton());
    buttons.setSpacing(25);
    buttons.setAlignment(Pos.CENTER);

    // on règle l'écart du contenu intérieur avec les bords de la boxe
    principalMenu.setPadding(new Insets(15, 15, 15, 15));
    principalMenu.setPrefWidth(WIDTH_WINDOW);

    // Espace entre les éléments
    principalMenu.setSpacing(100);

    // On lui applique d'autres styles présents dans la feuille CSS
    principalMenu.getStyleClass().add("header-hbox");

    // on ajoute les éléments à la boxe
    principalMenu.getChildren().addAll(imageMenuView, buttons);
    principalMenu.setAlignment(Pos.CENTER);

    // On met en place le corps de la fenêtre
    racine.setCenter(principalMenu);
    racine.setTop(barreNavigation);
  }

  /** PAGE DU MENU DE SETTINGS (nom player, adresse IP, port) */
  private void displaySettingsMenu() throws UnknownHostException {
    racine.setRight(null);
    racine.setLeft(null);
    racine.setTop(null);
    racine.setBottom(null);

    // ------------------------------------------------------------------
    // BARRE DE NAVIGATION DE LA PAGE SETTING MENU
    // ------------------------------------------------------------------

    HBox barreNavigation = defineHeader(true);

    // ------------------------------------------------------------------
    // CORPS DE LA PAGE MENU SETTINGS
    // ------------------------------------------------------------------
    VBox bodyParameters =
        new VBox(10); // contient les éléments de la page et les affiche verticalement.
    bodyParameters.getStyleClass().add("parameters-body");

    bodyParameters.prefWidthProperty().bind(currentStage.widthProperty().multiply(0.80));

    // Titre : Settings
    Label settingsTitle = new Label("Settings");
    settingsTitle.getStyleClass().add("instructions-title");

    // Nom du joueur (Bouffon n°1 si pas changé :D)
    Label playerName = new Label("Nom");
    playerName.getStyleClass().add("parameters-label");

    TextField playerNameField = new TextField();
    playerNameField.setText("Bouffon n°1");
    playerNameField.getStyleClass().add("parameters-field");

    // Adresse IP du joueur
    Label playerIP = new Label("Adresse IP");
    playerIP.getStyleClass().add("parameters-label");

    TextField playerIpField = new TextField();
    playerIpField.setText(
        String.valueOf(InetAddress.getLoopbackAddress())); // récupère l'adresse IP
    playerIpField.getStyleClass().add("parameters-field");

    // Port du joueur
    Label playerPort = new Label("Port");
    playerPort.getStyleClass().add("parameters-label");

    TextField playerPortField = new TextField();
    playerPortField.setText("8080"); // récupère l'adresse IP
    playerPortField.getStyleClass().add("parameters-field");

    // On crée un bouton qui va permettre de valider les paramètres et créer une nouvelle partie.
    Button validateParameters = new Button("Valider");
    validateParameters.getStyleClass().add("header-button");
    validateParameters.setOnAction(
        event -> {
          isGaming = true;
          try {
            // TODO : check que les bonnes données soient rentrées ?
            // On initialise les données
            namePlayer1 = playerNameField.getText();
            IpPlayer1 = playerIpField.getText();
            portPlayer1 = playerPortField.getText();

            // On passe à la fenêtre d'attente d'adversaire
            // TODO : remplacer par fenêtre de chargement d'adversaire.
            inGame(racine);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

    bodyParameters
        .getChildren()
        .addAll(
            settingsTitle,
            playerName,
            playerNameField,
            playerIP,
            playerIpField,
            playerPort,
            playerPortField,
            validateParameters);
    bodyParameters.setAlignment(Pos.CENTER);
    bodyParameters.setSpacing(50); // espace entre les éléments

    // ------------------------------------------------------------------
    // REGLAGES RACINE
    // ------------------------------------------------------------------
    racine.setTop(barreNavigation);
    racine.setCenter(bodyParameters);
  }

  /** Permet de choisir le personnage qui représentera le player. */
  private void chooseCharacter() {

    HBox barreNavigation = defineHeader(true);

    // ------------------------------------------------------------------
    // CORPS DE LA PAGE MENU SETTINGS
    // ------------------------------------------------------------------
    VBox bodyParameters =
        new VBox(10); // contient les éléments de la page et les affiche verticalement.
    bodyParameters.getStyleClass().add("parameters-body");

    bodyParameters.prefWidthProperty().bind(currentStage.widthProperty().multiply(0.80));

    // On crée un bouton qui va permettre de valider les paramètres et créer une nouvelle partie.
    Button validateImageCharacter = new Button("Choisir");
    validateImageCharacter.getStyleClass().add("header-button");
    validateImageCharacter.setOnAction(
        event -> {
          isGaming = true;
          try {

            // On passe à la fenêtre d'attente d'adversaire
            // TODO : remplacer par fenêtre de chargement d'adversaire.
            inGame(racine);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

    bodyParameters.getChildren().addAll(validateImageCharacter);
    bodyParameters.setAlignment(Pos.CENTER);
    bodyParameters.setSpacing(50); // espace entre les éléments

    // ------------------------------------------------------------------
    // REGLAGES RACINE
    // ------------------------------------------------------------------
    racine.setTop(barreNavigation);
    racine.setCenter(bodyParameters);
  }

  private HBox defineHeader(boolean isReturnMenu) {
    racine.setRight(null);
    racine.setLeft(null);
    racine.setTop(null);
    racine.setBottom(null);

    LinkedList<GameButton> buttons = new LinkedList<>();

    if (isReturnMenu) {
      // permet de retourner au menu principal
      GameButton returnToMenu = new GameButton("Retourner au menu", "header-button");
      returnToMenu
          .getButton()
          .setOnAction(
              actionEvent -> {
                try {
                  inMainMenu();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });

      buttons.add(returnToMenu);
    }
    else if(isGaming)
    {
      // valide le tour
      GameButton validateTourButton = new GameButton("Valider tour", "header-button");
      // On définit le bouton validerTour
      validateTourButton
              .getButton()
              .setOnAction(
                      actionEvent -> {
                        // blablabla définir ce que fait le bouton "valider tour" ici.
                        EndTurn endTurn = new EndTurn();
                        endTurn.setPlayer(player1);

                        // TODO send this to backend
                        System.out.println(endTurn.toJson());

                        System.out.println("you hit the validate button...");
                      });

      // abandonne la partie
      GameButton abandonTourButton = new GameButton("Abandonner", "header-button");
      abandonTourButton
              .getButton()
              .setOnAction(
                      actionEvent -> {
                        // TODO : ajouter un pop-up qui demander si on veut vraiment abandonner.
                        EndGame endGame = new EndGame();
                        endGame.setPlayerName(player1.getName());
                        endGame.setPlayerState('L');

                        // TODO send this to backend
                        System.out.println(endGame.toJson());
                      });

      buttons.add(validateTourButton);
      buttons.add(abandonTourButton);
    }

    NavigationBar navigationBar = new NavigationBar(buttons, "header-hbox", isGaming);
    navigationBar.generate();

    return navigationBar.getBarreNavigation();
  }

  /**
   * PAGE INSTRUCTIONS
   *
   * @throws IOException
   */
  private void displayInstructions() throws IOException {
    racine.setRight(null);
    racine.setLeft(null);
    racine.setTop(null);
    racine.setBottom(null);

    // ------------------------------------------------------------------
    // BARRE DE NAVIGATION DE LA PAGE INSTRUCTIONS
    // ------------------------------------------------------------------

    // On créé une boxe horizontale qui définira l'espace "navigation".
    HBox barreNavigation = defineHeader(true);

    // ------------------------------------------------------------------
    // CORPS DE LA PAGE INSTRUCTIONS
    // ------------------------------------------------------------------
    Label instructionsTitle = new Label("Instructions");
    instructionsTitle.getStyleClass().add("instructions-title");
    VBox corpsInstruction = new VBox();
    corpsInstruction.getStyleClass().add("instructions-body");

    String line, fullText = "";
    try (BufferedReader reader =
        new BufferedReader(new FileReader(new File("src/main/resources/utils/instructions.txt")))) {

      while ((line = reader.readLine()) != null) fullText += line;

    } catch (IOException e) {
      e.printStackTrace();
    }

    Text gameInstructions = new Text(fullText);
    gameInstructions.getStyleClass().add("instructions-text");
    gameInstructions.setWrappingWidth(900);

    VBox textBox = new VBox();
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().add(gameInstructions);

    corpsInstruction.getChildren().addAll(instructionsTitle, textBox);
    corpsInstruction.setAlignment(Pos.CENTER);
    corpsInstruction.setSpacing(50);

    // ------------------------------------------------------------------
    // REGLAGES RACINE
    // ------------------------------------------------------------------

    racine.setTop(barreNavigation);
    racine.setCenter(corpsInstruction);
  }

  /**
   * Modélise l'affichage d'une partie en cours
   *
   * @param racine : le borderpane sur lequel est affiché la page
   * @throws IOException
   */
  private void inGame(BorderPane racine) throws IOException {

    isGaming = true;

    // On crée une barre de navigation dans le BorderPane
    racine.setTop(defineHeader(false));

    // On met en place le corps du texte
    racine.setCenter(displayInGameField());

    // On créé l'espace d'infos du joueur 1
    racine.setLeft(getPlayerInformations(actionPlayer1Labels));

    // On créé l'espace d'infos du joueur 1
    racine.setRight(getPlayerInformations(actionPlayer2Labels));

    // On crée un footer dans le BorderPane
    racine.setBottom(footerBar());
  }

  /** @return les informations du player 1 (gauche) */
  private VBox getPlayerInformations(ListView actionPlayerLabel) {
    VBox informationPannelUser = new VBox();

    // On l'affiche
    informationPannelUser.getStyleClass().add("menuLabelsGauche-vbox");

    // On créé le titre "Actions"
    Label informationPannelUserTitle = new Label("Joueur 1");

    // On le stylise grâce à la feuille CSS
    informationPannelUserTitle.getStyleClass().add("titre-label");

    // On l'affiche
    informationPannelUser.getChildren().add(informationPannelUserTitle);

    // On initialise la liste d'actions liées au tour du joueur
    actionPlayerLabel = new ListView();
    actionPlayerLabel.getStyleClass().add("actionPlayer");

    // On fait en sorte qu'ils soient cliquables
    actionPlayerLabel.setCellFactory(TextFieldListCell.forListView());
    actionPlayerLabel.setEditable(true);

    informationPannelUser.getChildren().add(actionPlayerLabel);
    return informationPannelUser;
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

  public void setPlayer1(Player player1) {
    this.player1 = player1;
  }

  public void setPlayer2(Player player2) {
    this.player2 = player2;
  }
}
