package gui;

import gameLogic.commands.guiCommands.EndGame;
import gameLogic.commands.playersAction.EndTurn;
import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
import gameLogic.receptors.Receptor;
import gui.board.GUIBoard;
import gui.buttons.GameButton;
import gui.gameWindows.CharacterWindow;
import gui.gameWindows.InGameWindow;
import gui.gameWindows.InstructionWindow;
import gui.gameWindows.ParameterWindow;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

  private Player player1, player2;
  private LinkedList<Card> deck1, deck2;
  private String namePlayer1 = "", IpPlayer1 = "", portPlayer1 = "";

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
  private void chooseCharacter() throws FileNotFoundException {
    CharacterWindow characterWindow =
        new CharacterWindow(racine, defineHeader(true), currentStage, WIDTH_WINDOW, player1);

    // On crée un bouton qui va permettre de valider les paramètres et créer une nouvelle partie.
    GameButton validateImageCharacter = new GameButton("Choisir", "header-button");
    validateImageCharacter
        .getButton()
        .setOnAction(
            event -> {
              isGaming = true;
              try {
                // On récupère la sélection du joueur
                ((Receptor) player1).setImgPath(characterWindow.defineSelectedUrl());

                // On passe à la fenêtre d'attente d'adversaire
                // TODO : remplacer par fenêtre de chargement d'adversaire.
                inGame(racine);
              } catch (IOException e) {
                e.printStackTrace();
              }
            });

    characterWindow.getCorps().getChildren().add(validateImageCharacter.getButton());
    racine.setCenter(characterWindow.getCorps());
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
                // blablabla définir ce que fait le bouton "valider tour" ici.
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
                System.out.println(endGame.toJson());

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
            currentStage);
  }

  public GUIBoard getGUIBoard() {
    return GUIBoard;
  }

  public void place(Receptor receptor, int line, int position) {
    GUIBoard.place(receptor, line, position);
    GUIBoard.getLine(line).getSpot(position).setOccupant(receptor);
  }
}
