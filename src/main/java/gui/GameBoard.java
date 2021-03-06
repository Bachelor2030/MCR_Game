package gui;

import gui.board.GUIBoard;
import gui.buttons.GameButton;
import gui.gameWindows.*;
import gui.receptors.GUICard;
import gui.receptors.GUIPlayer;
import gui.receptors.GUIReceptor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import network.ClientAdapter;
import network.ClientRunner;
import network.Messages;
import network.ServerAdapter;
import network.jsonUtils.GUIParser;
import network.utilities.JsonClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.System.exit;

// TODO : commande qui font des actions graphiques. (genre déplacer créature)

/** Permet de représenter l'entierté du jeu */
public class GameBoard extends Application {

  // ******************************************************************
  // ATTRIBUTS DE LA CLASSE
  // ******************************************************************

  // Taille fenêtre
  public static final int WIDTH_WINDOW = 1400;
  public static final int HEIGHT_WINDOW = 750;

  // Cors du jeu -> là où se trouvent les îles + créatures & shit
  private GridPane gridIslandsPanel;

  private static boolean isGaming; // les joueurs sont-ils en partie ?

  private BorderPane racine; // la racine de l'affichage

  private GUIPlayer player1, player2; // les joueurs
  private GUIParser guiParser; // informations du tour pour le joueur
  private ArrayList<GUICard> handPlayer; // la main du joueur dont c'est le tour
  private final String jsonPath = "src/main/resources/json/";
  private String namePlayer1 = "",
      IpPlayer1 = "",
      portPlayer1 = ""; // informations liées au joueur1

  private GUIBoard guiBoard; // le plateau du jeu

  private Stage currentStage; // le stage lié à la GUI

  private ClientAdapter clientAdapter; // l'état partagé du client

  private boolean serverIsOn = false;
  ServerAdapter server;
  private InGameWindow inGameWindow;

  /** Thread principal du GUI. Gère l'affichage général de la "scene". */
  @Override
  public void start(Stage stage) throws Exception {
    currentStage = stage;

    // On initialise les players
    player1 = new GUIPlayer();

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
    // stage.setMaximized(false);
    stage.setResizable(true);

    stage.setTitle("MCR - BACHELOR HUNTERZ");
    stage.initStyle(StageStyle.DECORATED);
    stage.show();
  }

  /**
   * Permet de terminer le jeu en testant si la partie est terminée et afficher les
   * vainqueur/perdant
   */
  public void exitGame() {
    isGaming = false;

    // TODO afficher la fenêtre de fin
    // GameOverWindow gameOverWindow =
    // new GameOverWindow(racine, defineHeader(true), currentStage, isGaming, player1, player2);

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
    instructionButton
        .getButton()
        .setOnAction(
            actionEvent -> {
              displayInstructions();
            });

    // NEW GAME BUTTON
    GameButton newGameButton = new GameButton("Nouvelle partie", "bouton-menu-principal");
    newGameButton
        .getButton()
        .setOnAction(
            actionEvent -> {
              displaySettingsMenu();
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

  /** PAGE DU MENU DE SETTINGS (nom Player, adresse IP, port) */
  private void displaySettingsMenu() {

    ParameterWindow parameterWindow =
        new ParameterWindow(racine, defineHeader(true), currentStage, isGaming);

    Label serverLaunchedLabel = new Label("Server launched. Can't stop server for now");

    // On crée un bouton pour lancer le serveur
    GameButton startServer = new GameButton("Lancer Serveur", "bouton-menu-principal");
    startServer
        .getButton()
        .setOnAction(
            event -> {
              if (!serverIsOn) {
                serverIsOn = true;
                startServer.getButton().setDisable(true);
                parameterWindow.getBody().getChildren().add(serverLaunchedLabel);
                server =
                    new ServerAdapter(
                        Integer.valueOf(parameterWindow.getPlayerPortField().getText()), 4, 12);
                server.serveClients();
              }
            });

    parameterWindow.addGameButton(startServer);

    // On crée un bouton qui va permettre de valider les paramètres et créer une nouvelle partie.
    GameButton validateParameters = new GameButton("Valider", "bouton-menu-principal");
    validateParameters
        .getButton()
        .setOnAction(
            event -> {
              isGaming = false;
              try {
                // On initialise les données
                namePlayer1 = parameterWindow.getPlayerNameField().getText();
                // TODO: Remove this, only for debugging
                if (serverIsOn && namePlayer1.equals(parameterWindow.defaultName))
                  namePlayer1 = "Admin";
                IpPlayer1 = parameterWindow.getPlayerIpField().getText();
                portPlayer1 = parameterWindow.getPlayerPortField().getText();

                player1.setName(namePlayer1);

                clientAdapter =
                    new ClientAdapter(this, IpPlayer1, Integer.parseInt(portPlayer1), namePlayer1);
                new Thread(new ClientRunner(clientAdapter)).start();

                // On passe à la fenêtre de choix de character
                chooseCharacter();

              } catch (IOException e) {
                e.printStackTrace();
              }
            });

    parameterWindow.addGameButton(validateParameters);
    racine.setCenter(parameterWindow.getBody());
  }

  /** Permet de choisir le personnage qui représentera le Player. */
  private void chooseCharacter() throws IOException {
    CharacterWindow characterWindow =
        new CharacterWindow(racine, defineHeader(true), currentStage, WIDTH_WINDOW, player1);

    // On crée un bouton qui va permettre de valider les paramètres et créer une nouvelle partie.
    GameButton validateImageCharacter = new GameButton("Choisir", "header-button");
    validateImageCharacter
        .getButton()
        .setOnAction(
            event -> {
              try {
                isGaming = false;

                // On récupère la sélection du joueur
                player1.setImgPath(characterWindow.defineSelectedUrl());

                // On passe à la fenêtre d'attente d'adversaire
                waitingForPlayer();
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

    characterWindow.getBody().getChildren().add(validateImageCharacter.getButton());
    racine.setCenter(characterWindow.getBody());
  }

  /**
   * Attend la connexion d'un autre player
   * @throws IOException
   */
  private void waitingForPlayer() throws IOException {
    WaitingWindow waitingWindow =
        new WaitingWindow(racine, defineHeader(false), false, currentStage);
    racine.setCenter(waitingWindow.getBody());
    waitingWindow.execute();

    while (!clientAdapter.getClientSharedState().isFinishedInit()) {
      // racine.setCenter(waitingWindow.getBody());
      // waitingWindow.execute();
    }

    inGame(racine);
  }

  /**
   * Définit les différents header de chaque page
   *
   * @param isReturnMenu : y a-t-il un bouton "retour menu" ?
   * @return la barre de navigation sous forme de HBox.
   */
  private HBox defineHeader(boolean isReturnMenu) {
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
                  // TODO : afficher alert : voulez vous déclarer forfait ?
                  System.out.println("you hit the returnMenu button...");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });

      buttons.add(returnToMenu);
    } else if (isGaming) {
      // valide le tour
      GameButton validateTourButton = new GameButton("Valider tour", "header-button");
      // On définit le bouton validerTour
      validateTourButton
          .getButton()
          .setOnAction(
              actionEvent -> {
                displayNotYourTurnAlert();
                if (!clientAdapter.getClientSharedState().isMyTurn()) {
                  System.out.println("Please wait for your turn to validate it");
                  return;
                } else {
                  JSONObject json = null;
                  try {
                    json = JsonClient.jsonType(Messages.JSON_TYPE_END_TURN);
                  } catch (JSONException e) {
                    e.printStackTrace();
                  }
                  clientAdapter.getClientSharedState().pushJsonToSend(json);
                  clientAdapter.getClientSharedState().setIntendToSendJson(true);
                }
              });

      // abandonne la partie
      GameButton abandonTourButton = new GameButton("Abandonner", "header-button");
      abandonTourButton
          .getButton()
          .setOnAction(
              actionEvent -> {
                displayNotYourTurnAlert();
                if (!clientAdapter.getClientSharedState().isMyTurn()) {
                  System.out.println("Please wait for your turn to validate it");
                  return;
                } else {
                  AskIfReallyWantToQuit();
                }
              });

      GameButton undoButton = new GameButton("Undo", "header-button");
      undoButton
          .getButton()
          .setOnAction(
              actionEvent -> {
                displayNotYourTurnAlert();
                // TODO implement undo button
                System.out.println("you hit the undo button...");
              });

      buttons.add(validateTourButton);
      buttons.add(undoButton);
      buttons.add(abandonTourButton);
    }

    NavigationBar navigationBar = new NavigationBar(buttons, "header-hbox", isGaming);
    navigationBar.generate();

    return navigationBar.getBarreNavigation();
  }

  /**
   * Demande à la personne si elle a vraiment envie de quitter la partie
   */
  private void AskIfReallyWantToQuit() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Quitter l'application");
    alert.setHeaderText("");
    alert.setContentText("Vous êtes en pleine partie, voulez-vous réellement quitter le jeu ?");

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
      JSONObject json = null;
      try {
        json = JsonClient.jsonType(Messages.JSON_TYPE_GAME_END);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      clientAdapter.getClientSharedState().pushJsonToSend(json);
      clientAdapter.getClientSharedState().setIntendToSendJson(true);

      exit(0);
    }
  }

  /**
   * Affiche une alerte indiquant que ce n'est pas notre tour
   */
  private void displayNotYourTurnAlert() {
    if (!player1.getClientSharedState().isMyTurn()) {
      // on créé une alerte WARNING qui indique à l'utilisateur
      // que ce n'est pas à lui de jouer.
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setAlertType(Alert.AlertType.WARNING);
      alert.setTitle("Ce n'est pas à votre tour de jouer !");
      Image image =
          new Image(
              "https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Emojione_1F62D.svg/64px-Emojione_1F62D.svg.png");
      ImageView imageView = new ImageView(image);
      alert.setGraphic(imageView);
      DialogPane dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/design/css/styleSheet.css").toExternalForm());
      alert.show();
    }
  }

  /** PAGE INSTRUCTIONS */
  private void displayInstructions() {
    InstructionWindow instructionWindow =
        new InstructionWindow(racine, defineHeader(true), isGaming, currentStage);
  }

  /**
   * Modélise l'affichage d'une partie en cours
   *
   * @param racine : le borderpane sur lequel est affiché la page
   * @throws IOException
   */
  public void inGame(BorderPane racine) throws IOException {
    isGaming = true;

    guiBoard = new GUIBoard(new GridPane(), new VBox(), player1, player2);

    inGameWindow =
        new InGameWindow(
            racine,
            defineHeader(false),
            guiBoard,
            player1,
            player2,
            isGaming,
            clientAdapter.getClientSharedState(),
            currentStage,
            handPlayer);
  }

  /**
   * @return le guiboard
   */
  public GUIBoard getGuiBoard() {
    return guiBoard;
  }

  /**
   * place le récepteur à la position donnée
   * @param receptor : le récepteur à placer
   * @param line : la ligne
   * @param position : le spot
   */
  public void place(GUIReceptor receptor, int line, int position) {
    guiBoard.place(receptor, line, position);
    guiBoard.getLine(line).getSpot(position).setOccupant(receptor);
  }

  /**
   * @return le client adapter
   */
  public ClientAdapter getClientAdapter() {
    return clientAdapter;
  }

  /**
   * Permet de modifier le ClientAdapter
   * @param clientAdapter : le client adapter
   */
  public void setClientAdapter(ClientAdapter clientAdapter) {
    this.clientAdapter = clientAdapter;
  }

  /**
   * @return la main du joueur
   */
  public ArrayList<GUICard> getHandPlayer() {
    return handPlayer;
  }

  /**
   * Envoie l'état initial au GUI (l'état au commencement de la partie)
   * @param initMessage : les informationsà parser
   */
  public void sendInit(String initMessage) {
    GUIParser guiParser = new GUIParser(initMessage, clientAdapter.getClientSharedState());

    handPlayer = guiParser.getCardsFromInit();
    player1.addHand(handPlayer);
    player1.setClientSharedState(clientAdapter.getClientSharedState());

    player2 =
        new GUIPlayer(
            guiParser.getEnemyFromInit()[0],
            guiParser.getEnemyFromInit()[1],
            new ArrayList<>(),
            clientAdapter.getClientSharedState());
    clientAdapter.getClientSharedState().setMyTurn(guiParser.getTurnFromInit());
  }

  /**
   * Ajoute un carte à la main du joueur
   * @param card : la carte à ajouter
   */
  public void addCard(GUICard card) {
    player1.addToHand(card);
    try {
      inGameWindow.generateBody();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retire une carte de la main du joueur
   * @param cardID : l'id de la carte
   */
  public void removeCard(int cardID) {
    player1.removeFromHand(cardID);
    try {
      inGameWindow.generateBody();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Permet de placer un piège
   * @param line : la ligne
   * @param position : le spot
   */
  public void placeTrap(int line, int position) {
    guiBoard.placeTrap(line, position);
    DropShadow shadow = new DropShadow();
    shadow.setColor(Color.RED);
    guiBoard.getLine(line).getSpot(position).getButton().setEffect(shadow);
  }

  /**
   * Permet d'enlever un piège
   * @param line : la ligne
   * @param position : le spot
   */
  public void removeTrap(int line, int position) {
    guiBoard.removeTrap(line, position);
    guiBoard.getLine(line).getSpot(position).getButton().getStyleClass().add("button-island");
  }

  /**
   * Met la vue à jour
   */
  public void updateStage() {
    System.out.println("Updating stage");
    currentStage.show();
  }
}
