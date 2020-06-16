package gui;

import gameLogic.commands.guiCommands.EndGame;
import gameLogic.commands.playersAction.EndTurn;
import gameLogic.receptors.Player;
import gameLogic.receptors.Receptor;
import gui.board.GUIBoard;
import gui.buttons.GameButton;
import gui.gameWindows.*;
import gui.receptors.GUIPlayer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import network.ClientAdapter;
import network.ClientRunner;
import network.ServerAdapter;
import network.jsonUtils.GUIParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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

  private static boolean isGaming;

  private BorderPane racine;

  private GUIPlayer player1, player2;
  private GUIParser guiParser; // informations du tour pour le joueur
  private ArrayList<GUICard> handPlayer;
  private final String jsonPath = "src/main/resources/json/";
  private String namePlayer1 = "", IpPlayer1 = "", portPlayer1 = "";

  private GUIBoard GUIBoard;

  private Stage currentStage;

  private ClientAdapter clientAdapter;

  private boolean serverIsOn = false;
  ServerAdapter server = new ServerAdapter(1337, 4, 12);

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
    stage.setMaximized(true);
    stage.setTitle("MCR - BACHELOR HUNTERZ");
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.show();

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

    // On crée un bouton pour lancer le serveur
    GameButton startServer = new GameButton("Lancer Serveur", "header-button");
    startServer
            .getButton()
            .setOnAction(
                    event -> {
                      // TODO
                      serverIsOn =  !serverIsOn;
                      if (serverIsOn) {
                        startServer.getButton().setText("Server launched.\nCan't stop server for now");
                        server.serveClients();
                      } else {
                        startServer.getButton().setText("Server launched.\nCan't stop server for now");
                        //server.getServerSharedState().endGame();
                        //server.closeClientSocket();
                      }

                    }
            );


    parameterWindow.addGameButton(startServer);

    // On crée un bouton qui va permettre de valider les paramètres et créer une nouvelle partie.
    GameButton validateParameters = new GameButton("Valider", "header-button");
    validateParameters
        .getButton()
        .setOnAction(
            event -> {
              isGaming = false;
              try {
                // On initialise les données
                namePlayer1 = parameterWindow.getPlayerNameField().getText();
                IpPlayer1 = parameterWindow.getPlayerIpField().getText();
                portPlayer1 = parameterWindow.getPlayerPortField().getText();

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

  private void waitingForPlayer() throws IOException {
    WaitingWindow waitingWindow =
        new WaitingWindow(racine, defineHeader(false), false, currentStage);
    racine.setCenter(waitingWindow.getBody());
    waitingWindow.execute();

    while(!clientAdapter.getClientSharedState().isFinishedInit()) {
      //racine.setCenter(waitingWindow.getBody());
      //waitingWindow.execute();
    }

    //TODO pecho info joueur2
    //TODO initialisation deck
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
                //TODO : envoyer au backend
                EndTurn endTurn = new EndTurn();
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
                // System.out.println(endGame.toJson());
                System.out.println("you hit the abandon button...");
              });
      buttons.add(validateTourButton);
      buttons.add(abandonTourButton);
    }

    NavigationBar navigationBar = new NavigationBar(buttons, "header-hbox", isGaming);
    navigationBar.generate();

    return navigationBar.getBarreNavigation();
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
    InGameWindow inGameWindow =
        new InGameWindow(
            racine,
            defineHeader(false),
            gridIslandsPanel,
            GUIBoard,
            player1,
            player2,
            isGaming,
            currentStage, handPlayer);
  }

  public GUIBoard getGUIBoard() {
    return GUIBoard;
  }

  public void place(Receptor receptor, int line, int position) {
    GUIBoard.place(receptor, line, position);
    GUIBoard.getLine(line).getSpot(position).setOccupant(receptor);
  }

  public ClientAdapter getClientAdapter() {
    return clientAdapter;
  }

  public void setClientAdapter(ClientAdapter clientAdapter) {
    this.clientAdapter = clientAdapter;
  }

  public ArrayList<GUICard> getHandPlayer() {
    return handPlayer;
  }

  public void sendInit(String initMessage) {
    GUIParser guiParser = new GUIParser(initMessage);
    // TODO Récup' les infos du deck et tout le bordel et l'afficher ou il faut
    handPlayer = guiParser.getCardsFromInit();
    player1.addHand(handPlayer);

    player2 = new GUIPlayer(guiParser.getEnemyFromInit()[0], guiParser.getEnemyFromInit()[1], new ArrayList<>());
  }
}
