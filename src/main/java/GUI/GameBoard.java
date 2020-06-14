package GUI;

import GameLogic.Commands.GuiCommands.EndGame;
import GameLogic.Commands.GuiCommands.Move;
import GameLogic.Board.Board;
import GameLogic.Receptors.Creature;
import GameLogic.Receptors.Player;
import GameLogic.Invocator.Card.Card;
import GameLogic.Commands.PlayersAction.EndTurn;
import GameLogic.Receptors.Receptor;
import GameLogic.Board.Position;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
  final int WIDTH_WINDOW = 1200;
  final int HEIGHT_WINDOW = 800;

  // Buttons
  private Button validateTourButton; // valide le tour
  private Button abandonTourButton; // abandonne la partie
  private Button quitButton; // quitte le jeu
  private Button minimizeButton; // rabat la fenêtre de jeu
  private Button instructionButton; // affiche le menu d'instruction
  private Button newGameButton; // commence une nouvelle partie
  private Button returnToMenu; // permet de retourner au menu principal

  // Cors du jeu -> là où se trouvent les îles + créatures & shit
  private GridPane gridIslandsPanel;

  // Affiche les actions joueurs par le player 1
  private ListView<String> actionPlayer1Labels;
  private Label deleteActionLabelP1;

  // Affiche les actions joueurs par le player 1
  private ListView<String> actionPlayer2Labels;
  private Label deleteActionLabelP2;

  private boolean isGaming;

  private BorderPane racine;

  private Player player1, player2;
  private LinkedList<Card> deck1, deck2;
  private String
          namePlayer1, namePlayer2,
          IpPlayer1, IpPlayer2,
          portPlayer1, portPlayer2;

  private Board board;

  private Stage currentStage;

  /** Thread principal du GUI. Gère l'affichage général de la "scene". */
  @Override
  public void start(Stage stage) throws Exception {
    currentStage = stage;
    //On initialise les players
    player1 = new Player();
    player2 = new Player();


    // Racine de scene
    racine = new BorderPane();

    // fond de base
    racine.getStyleClass().add("general-borderPanel");

    // Affichage du menu principal
    inMainGameMenu();

    // ------------------------------------------------------------------
    // PARAMÈTRES DE LA FENÊTRE DU LOGICIEL
    // ------------------------------------------------------------------

    Scene scene = new Scene(racine, WIDTH_WINDOW, HEIGHT_WINDOW);

    // on définit le curseur comme croix
    //scene.setCursor(Cursor.CROSSHAIR);

    // on applique les styles de la feuille CSS
    scene.getStylesheets().add("/design/css/styleSheet.css");

    stage.setScene(scene);
    // met la fenêtre au max
    stage.setMaximized(true);
    stage.setTitle("MCR - JEU DE LA MUERTA");
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.show();
  }

  public void setPlayer1(Player player1) {
    this.player1 = player1;
  }

  public void setPlayer2(Player player2) {
    this.player2 = player2;
  }

  public Board getBoard() {
    return board;
  }

  public void place(Receptor receptor, int line, int position) {
    board.place(receptor, line, position);
    board.getLine(line).getSpot(position).setOccupant(receptor);
  }

  public void exitGame() {
    // TODO afficher la fenêtre de fin
    isGaming = false;
    try {
      inMainGameMenu();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * MENU PRINCIPAL DU JEU
   *
   * @throws IOException
   */
  private void inMainGameMenu() throws IOException {
    racine.setRight(null);
    racine.setLeft(null);
    racine.setTop(null);
    racine.setBottom(null);

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
    instructionButton = new Button("Instructions");
    instructionButton.getStyleClass().add("bouton-menu-principal");
    instructionButton.setOnAction(
        actionEvent -> {
          try {
            displayInstructions();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });

    // NEW GAME BUTTON
    newGameButton = new Button("Nouvelle partie");
    newGameButton.getStyleClass().add("bouton-menu-principal");
    newGameButton.setOnAction(
        actionEvent -> {
          try {
            displaySettingsMenu();
          } catch (UnknownHostException e) {
            e.printStackTrace();
          }
        });

    buttons.getChildren().addAll(instructionButton, newGameButton);
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

  /**
   * PAGE DU MENU DE SETTINGS (nom player, adresse IP, port)
   */
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
    VBox bodyParameters = new VBox(10); //contient les éléments de la page et les affiche verticalement.
    bodyParameters.getStyleClass().add("parameters-body");

    bodyParameters.prefWidthProperty().bind(currentStage.widthProperty().multiply(0.80));

    //Titre : Settings
    Label settingsTitle = new Label("Settings");
    settingsTitle.getStyleClass().add("instructions-title");

    //Nom du joueur (Bouffon n°1 si pas changé :D)
    Label playerName = new Label("Nom");
    playerName.getStyleClass().add("parameters-label");

    TextField playerNameField = new TextField();
    playerNameField.setText("Bouffon n°1");
    playerNameField.getStyleClass().add("parameters-field");

    //Adresse IP du joueur
    Label playerIP = new Label("Adresse IP");
    playerIP.getStyleClass().add("parameters-label");

    TextField playerIpField = new TextField();
    playerIpField.setText(String.valueOf(InetAddress.getLoopbackAddress())); //récupère l'adresse IP
    playerIpField.getStyleClass().add("parameters-field");

    //Port du joueur
    Label playerPort = new Label("Port");
    playerPort.getStyleClass().add("parameters-label");

    TextField playerPortField = new TextField();
    playerPortField.setText("8080"); //récupère l'adresse IP
    playerPortField.getStyleClass().add("parameters-field");

    //On crée un bouton qui va permettre de valider les paramètres et créer une nouvelle partie.
    Button validateParameters = new Button("Valider");
    validateParameters.getStyleClass().add("header-button");
    validateParameters.setOnAction(
            event -> {
              isGaming = true;
              try {
                //TODO : check que les bonnes données soient rentrées ?
                //On initialise les données
                namePlayer1 = playerNameField.getText();
                IpPlayer1 = playerIpField.getText();
                portPlayer1 = playerPortField.getText();

                //On passe à la fenêtre d'attente d'adversaire
                //TODO : remplacer par fenêtre de chargement d'adversaire.
                inGame(racine);
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

    bodyParameters.getChildren().addAll(settingsTitle,
            playerName, playerNameField,
            playerIP, playerIpField,
            playerPort, playerPortField,
            validateParameters);
    bodyParameters.setAlignment(Pos.CENTER);
    bodyParameters.setSpacing(50); //espace entre les éléments

    // ------------------------------------------------------------------
    // REGLAGES RACINE
    // ------------------------------------------------------------------
    racine.setTop(barreNavigation);
    racine.setCenter(bodyParameters);
  }

  /**
   * Permet de choisir le personnage qui représentera le player.
   */
  private void chooseCharacter() {

    HBox barreNavigation = defineHeader(true);

    // ------------------------------------------------------------------
    // CORPS DE LA PAGE MENU SETTINGS
    // ------------------------------------------------------------------
    VBox bodyParameters = new VBox(10); //contient les éléments de la page et les affiche verticalement.
    bodyParameters.getStyleClass().add("parameters-body");

    bodyParameters.prefWidthProperty().bind(currentStage.widthProperty().multiply(0.80));

    //On crée un bouton qui va permettre de valider les paramètres et créer une nouvelle partie.
    Button validateImageCharacter = new Button("Choisir");
    validateImageCharacter.getStyleClass().add("header-button");
    validateImageCharacter.setOnAction(
            event -> {
              isGaming = true;
              try {

                //On passe à la fenêtre d'attente d'adversaire
                //TODO : remplacer par fenêtre de chargement d'adversaire.
                inGame(racine);
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

    bodyParameters.getChildren().addAll(validateImageCharacter);
    bodyParameters.setAlignment(Pos.CENTER);
    bodyParameters.setSpacing(50); //espace entre les éléments

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

    // ------------------------------------------------------------------
    // BARRE DE NAVIGATION DE LA PAGE SETTING MENU
    // ------------------------------------------------------------------

    // On créé une boxe horizontale qui définira l'espace "navigation".
    HBox barreNavigation = new HBox(10);
    minimizeButton = new Button();
    minimizeButton.getStyleClass().add("header-quit-button");
    quitButton = new Button();
    quitButton.getStyleClass().add("header-quit-button");
    if(isReturnMenu)
    {
      returnToMenu = new Button("Retourner au menu");
      returnToMenu.getStyleClass().add("header-button");

      returnToMenu.setOnAction(
              actionEvent -> {
                try {
                  inMainGameMenu();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
      barreNavigation.getChildren().add(returnToMenu);
    }


    // on règle l'écart du contenu intérieur avec les bords de la boxe
    barreNavigation.setPadding(new Insets(15, 15, 15, 15));
    barreNavigation.setPrefWidth(WIDTH_WINDOW);

    // Espace entre les éléments
    barreNavigation.setSpacing(10);

    // On lui applique d'autres styles présents dans la feuille CSS
    barreNavigation.getStyleClass().add("header-hbox");

    // SEPARATEUR - séparer les utility buttons sur la droite
    // utility buttons : minimize, quit.
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    barreNavigation.getChildren().add(spacer);

    // MINIMIZE BUTTON
    Image minimizeIcon = new Image(getClass().getResourceAsStream("/design/images/minimize.png"));
    ImageView minimizeIconView = new ImageView(minimizeIcon);
    minimizeIconView.setFitHeight(21);
    minimizeIconView.setFitWidth(21);
    minimizeButton.setGraphic(minimizeIconView); // setting icon to button
    minimizeButton.setAlignment(Pos.CENTER_RIGHT);

    minimizeButton.setOnAction(
            event -> {
              Stage stage = (Stage) minimizeButton.getScene().getWindow();
              stage.setIconified(true);
            });

    barreNavigation.getChildren().add(minimizeButton);

    // QUIT BUTTON
    Image quitIcon = new Image(getClass().getResourceAsStream("/design/images/quit.png"));
    ImageView quitIconView = new ImageView(quitIcon);
    quitIconView.setFitHeight(21);
    quitIconView.setFitWidth(21);
    quitButton.setGraphic(quitIconView); // setting icon to button
    quitButton.setAlignment(Pos.CENTER_RIGHT);

    // On créé un event lié à la fermeture du logiciel.
    quitButton.setOnAction(
            event -> {
              ((Stage) quitButton.getScene().getWindow()).close();
              // sinon on affiche un message d'alerte -> redemande.
            });
    barreNavigation.getChildren().add(quitButton);

    return barreNavigation;
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

    // ------------------------------------------------------------------
    // BARRE DE NAVIGATION
    // ------------------------------------------------------------------

    validateTourButton = new Button("Valider tour");
    validateTourButton.getStyleClass().add("header-button");

    abandonTourButton = new Button("Abandonner");
    abandonTourButton.getStyleClass().add("header-button");

    minimizeButton = new Button();
    minimizeButton.getStyleClass().add("header-quit-button");

    quitButton = new Button();
    quitButton.getStyleClass().add("header-quit-button");

    // On crée une barre de navigation dans le BorderPane
    racine.setTop(defineInGameHeader());

    // ------------------------------------------------------------------
    // CORPS LOGICIEL OÙ SE TROUVE LES ÎLES
    // ------------------------------------------------------------------

    // On met en place le corps du texte
    racine.setCenter(displayInGameField());

    // ------------------------------------------------------------------
    // COLONNE GAUCHE - PLAYER 1
    // ------------------------------------------------------------------

    // On crée une colonne contenant les labels
    racine.setLeft(getPlayer1Informations());

    // ------------------------------------------------------------------
    // COLONNE GAUCHE - PLAYER 2
    // ------------------------------------------------------------------

    // On crée une colonne contenant les labels
    racine.setRight(getPlayer2Informations());

    // ------------------------------------------------------------------
    // FOOTER
    // ------------------------------------------------------------------

    // On crée un footer dans le BorderPane
    racine.setBottom(footerBar());
  }

  /** @return les informations du player 1 (gauche) */
  private VBox getPlayer1Informations() {
    VBox panneauVerticalGauche = new VBox();

    // On l'affiche
    panneauVerticalGauche.getStyleClass().add("menuLabelsGauche-vbox");

    // On créé le titre "Actions"
    Label titrePanneauLabeal = new Label("Joueur 1");

    // On le stylise grâce à la feuille CSS
    titrePanneauLabeal.getStyleClass().add("titre-label");

    // On l'affiche
    panneauVerticalGauche.getChildren().add(titrePanneauLabeal);

    // On initialise la liste d'actions liées au tour du joueur
    actionPlayer1Labels = new ListView();

    // On fait en sorte qu'ils soient cliquables
    actionPlayer1Labels.setCellFactory(TextFieldListCell.forListView());
    actionPlayer1Labels.setEditable(true);

    // On l'affiche et on le stylise
    panneauVerticalGauche.getChildren().add(actionPlayer1Labels);
    actionPlayer1Labels.getStyleClass().add("actionPlayer");

    // Permet de sélectionner plusieurs labels
    actionPlayer1Labels.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    // ----------------------------------------------------
    // DELETE LABEL BUTTON
    // ----------------------------------------------------
    deleteActionLabelP1 = new Label("");
    Button deleteLabelButton = new Button();
    panneauVerticalGauche
        .getChildren()
        .add(deleteLabelButton); // permet d'afficher l'élément dans le panneau
    panneauVerticalGauche
        .getChildren()
        .add(deleteActionLabelP1); // indique l'état de l'ajout d'un label

    Image deleteIcon = new Image(getClass().getResourceAsStream("/design/images/delete.png"));
    ImageView deleteIconView = new ImageView(deleteIcon);
    deleteIconView.setFitHeight(15);
    deleteIconView.setFitWidth(15);
    deleteLabelButton.setGraphic(deleteIconView); // setting icon to button
    deleteLabelButton.getStyleClass().add("left-button");

    // On créé un event lié à l'action de la suppression du label
    deleteLabelButton.setOnAction(
        e -> {
          try {

            // Permet de supprimer plusieurs éléments en même temps
            if (actionPlayer1Labels.getSelectionModel().getSelectedItems().size() > 1) {
              int taille = actionPlayer1Labels.getSelectionModel().getSelectedItems().size();
              while (taille > 0) {
                actionPlayer1Labels
                    .getItems()
                    .remove(actionPlayer1Labels.getSelectionModel().getSelectedItem());
                --taille;
              }
              deleteActionLabelP1.setText("Les actions ont été correctement supprimées.");
            }
            // condition pour un unique élément sélectionné
            else {
              actionPlayer1Labels
                  .getItems()
                  .remove(actionPlayer1Labels.getSelectionModel().getSelectedItem());
              deleteActionLabelP1.setText("L'action a été correctement supprimée.");
            }

          } catch (Exception ex) {
            deleteActionLabelP1.setText("Aucune action sélectionnée.");
          }
        });

    // marges extérieures des deux cases + buttons
    VBox.setMargin(actionPlayer1Labels, new Insets(10, 10, 10, 10));
    VBox.setMargin(deleteActionLabelP1, new Insets(1, 10, 10, 10));
    VBox.setMargin(deleteLabelButton, new Insets(1, 10, 1, 150));

    return panneauVerticalGauche;
  }

  private VBox getPlayer2Informations() {
    VBox panneauVerticalGauche = new VBox();

    // On l'affiche
    panneauVerticalGauche.getStyleClass().add("menuLabelsGauche-vbox");

    // On créé le titre "Actions"
    Label titrePanneauLabeal = new Label("Joueur 2");

    // On le stylise grâce à la feuille CSS
    titrePanneauLabeal.getStyleClass().add("titre-label");

    // On l'affiche
    panneauVerticalGauche.getChildren().add(titrePanneauLabeal);

    // On initialise la liste d'actions liées au tour du joueur
    actionPlayer2Labels = new ListView();

    // On fait en sorte qu'ils soient cliquables
    actionPlayer2Labels.setCellFactory(TextFieldListCell.forListView());
    actionPlayer2Labels.setEditable(true);

    // On l'affiche et on le stylise
    panneauVerticalGauche.getChildren().add(actionPlayer2Labels);
    actionPlayer2Labels.getStyleClass().add("actionPlayer");

    // Permet de sélectionner plusieurs labels
    actionPlayer2Labels.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    // ----------------------------------------------------
    // DELETE LABEL BUTTON
    // ----------------------------------------------------
    deleteActionLabelP2 = new Label("");
    Button deleteLabelButton = new Button();
    panneauVerticalGauche
        .getChildren()
        .add(deleteLabelButton); // permet d'afficher l'élément dans le panneau
    panneauVerticalGauche
        .getChildren()
        .add(deleteActionLabelP2); // indique l'état de l'ajout d'un label

    Image deleteIcon = new Image(getClass().getResourceAsStream("/design/images/delete.png"));
    ImageView deleteIconView = new ImageView(deleteIcon);
    deleteIconView.setFitHeight(15);
    deleteIconView.setFitWidth(15);
    deleteLabelButton.setGraphic(deleteIconView); // setting icon to button
    deleteLabelButton.getStyleClass().add("left-button");

    // On créé un event lié à l'action de la suppression du label
    deleteLabelButton.setOnAction(
        e -> {
          try {

            // Permet de supprimer plusieurs éléments en même temps
            if (actionPlayer2Labels.getSelectionModel().getSelectedItems().size() > 1) {
              int taille = actionPlayer2Labels.getSelectionModel().getSelectedItems().size();
              while (taille > 0) {
                actionPlayer2Labels
                    .getItems()
                    .remove(actionPlayer2Labels.getSelectionModel().getSelectedItem());
                --taille;
              }
              deleteActionLabelP2.setText("Les actions ont été correctement supprimées.");
            }
            // condition pour un unique élément sélectionné
            else {
              actionPlayer2Labels
                  .getItems()
                  .remove(actionPlayer2Labels.getSelectionModel().getSelectedItem());
              deleteActionLabelP2.setText("L'action a été correctement supprimée.");
            }

          } catch (Exception ex) {
            deleteActionLabelP2.setText("Aucune action sélectionnée.");
          }
        });

    // marges extérieures des deux cases + buttons
    VBox.setMargin(actionPlayer2Labels, new Insets(10, 10, 10, 10));
    VBox.setMargin(deleteActionLabelP2, new Insets(1, 10, 10, 10));
    VBox.setMargin(deleteLabelButton, new Insets(1, 10, 1, 150));

    return panneauVerticalGauche;
  }

  /**
   * Affiche le terrain où se déplace les créatures.
   * @return le gridPane sur lequel se déplace les créatures.
   * @throws IOException
   */
  private GridPane displayInGameField() throws IOException {

    VBox corpsInstruction = new VBox();
    corpsInstruction.getStyleClass().add("instructions-body");
    gridIslandsPanel = new GridPane();
    gridIslandsPanel.getStyleClass().add("corps-gridPane");
    VBox vbox = new VBox();

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
    board = new Board(gridIslandsPanel, vbox, player1, player2);
    gridIslandsPanel.setAlignment(Pos.CENTER);
    return gridIslandsPanel;
  }

  /**
   * @return la barre de navigation contenant les différents boutons gérant la partie.
   */
  private HBox defineInGameHeader() {
    // On créé une boxe horizontale qui définira l'espace "navigation".
    HBox navigation = new HBox(10);

    // on règle l'écart du contenu intérieur avec les bords de la boxe
    navigation.setPadding(new Insets(15, 15, 15, 15));
    navigation.setPrefWidth(WIDTH_WINDOW);

    // Espace entre les éléments
    navigation.setSpacing(10);

    // On lui applique d'autres styles présents dans la feuille CSS
    navigation.getStyleClass().add("header-hbox");

    // on ajoute les éléments à la boxe
    navigation.getChildren().addAll(validateTourButton, abandonTourButton);

    // On définit le bouton validerTour
    validateTourButton.setOnAction(
        actionEvent -> {
          // blablabla définir ce que fait le bouton "valider tour" ici.
          EndTurn endTurn = new EndTurn();
          endTurn.setPlayer(player1);

          // TODO send this to backend
          System.out.println(endTurn.toJson());

          System.out.println("you hit the validate button...");
        });

    abandonTourButton.setOnAction(
        actionEvent -> {
          // TODO : ajouter un pop-up qui demander si on veut vraiment abandonner.
          EndGame endGame = new EndGame();
          endGame.setPlayerName(player1.getName());
          endGame.setPlayerState('L');

          //TODO send this to backend
          System.out.println(endGame.toJson());
        });

    // ----------------------------------------------------
    // SEPARATEUR - séparer les utility buttons sur la droite
    // utility buttons : minimize, quit.
    // ----------------------------------------------------
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    navigation.getChildren().add(spacer);

    // ----------------------------------------------------
    // MINIMIZE BUTTON
    // ----------------------------------------------------

    Image minimizeIcon = new Image(getClass().getResourceAsStream("/design/images/minimize.png"));
    ImageView minimizeIconView = new ImageView(minimizeIcon);
    minimizeIconView.setFitHeight(21);
    minimizeIconView.setFitWidth(21);
    minimizeButton.setGraphic(minimizeIconView); // setting icon to button
    minimizeButton.setAlignment(Pos.CENTER_RIGHT);

    minimizeButton.setOnAction(
        event -> {
          Stage stage = (Stage) minimizeButton.getScene().getWindow();
          stage.setIconified(true);
        });

    navigation.getChildren().add(minimizeButton);

    // ----------------------------------------------------
    // QUIT BUTTON
    // ----------------------------------------------------
    Image quitIcon = new Image(getClass().getResourceAsStream("/design/images/quit.png"));
    ImageView quitIconView = new ImageView(quitIcon);
    quitIconView.setFitHeight(21);
    quitIconView.setFitWidth(21);
    quitButton.setGraphic(quitIconView); // setting icon to button
    quitButton.setAlignment(Pos.CENTER_RIGHT);

    // On créé un event lié à la fermeture du logiciel.
    // Si on quitte sans avoir exporté notre travail, cela affiche une alerte.
    quitButton.setOnAction(
        event -> {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Quitter l'application");
          alert.setHeaderText("");
          alert.setContentText(
              "Vous êtes en pleine partie, voulez-vous réellement quitter le jeu ?");

          // enlève les boutons de base de la fenêtre.
          alert.initStyle(StageStyle.UNDECORATED);
          ButtonType oui = new ButtonType("Quitter");
          ButtonType non = new ButtonType("Annuler");

          alert.getButtonTypes().setAll(oui, non);

          DialogPane dialogPane = alert.getDialogPane();
          dialogPane
              .getStylesheets()
              .add(getClass().getResource("/design/css/styleSheet.css").toExternalForm());

          alert.showAndWait();
          if (alert.getResult() == oui) {
            ((Stage) quitButton.getScene().getWindow()).close();
          }
        });
    navigation.getChildren().add(quitButton);

    return navigation;
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
