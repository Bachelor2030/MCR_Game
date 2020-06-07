package Client.View;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/** Permet de représenter l'entierté du jeu */
public class GameBoard extends Application {
  //TAILLE FENÊTRE
  final int WIDTH_WINDOW = 1200;
  final int HEIGHT_WINDOW = 800;

  //BUTTONS
  private Button validateTourButton;
  private Button abandonTourButton;
  private Button quitButton;
  private Button minimizeButton;

  //Cors du jeu -> là où se trouvent les îles + créatures & shit
  private StackPane sp;

  //Affiche les actions joueurs par le player 1
  private ListView<String> actionPlayer1Labels;
  private Label deleteActionLabel;

  //INDICATION JEU FINI
  boolean gameOver;

  @Override
  public void start(Stage stage) throws Exception {
    //------------------------------------------------------------------
    // DEFINITION DE LA FENÊTRE
    //------------------------------------------------------------------
    //Racine de scene
    BorderPane racine = new BorderPane();
    //fond de base
    racine.getStyleClass().add("general-borderPanel");

    //------------------------------------------------------------------
    // BARRE DE NAVIGATION
    //------------------------------------------------------------------


    validateTourButton = new Button("Valider tour");
    //validateTourButton.getStyleClass().add("header-button");
/*
    abandonTourButton = new Button("Abandonner");
    //abandonTourButton.getStyleClass().add("header-button");

    minimizeButton = new Button();
    //minimizeButton.getStyleClass().add("header-quit-button");

    quitButton = new Button();
    //quitButton.getStyleClass().add("header-quit-button");

    //On crée une barre de navigation dans le BorderPane
    racine.setTop(navBar());

    //------------------------------------------------------------------
    // CORPS LOGICIEL OÙ SE TROUVE LES ÎLES
    //------------------------------------------------------------------

    //On met en place le corps du texte
    racine.setCenter(corpsLogiciel());

    //------------------------------------------------------------------
    // COLONNE GAUCHE - PLAYER 1
    //------------------------------------------------------------------

    //On crée une colonne contenant les labels
    racine.setLeft(getPlayer1Informations());

    //------------------------------------------------------------------
    // FOOTER
    //------------------------------------------------------------------

    //On crée un footer dans le BorderPane
    racine.setBottom(footerBar());

     */

    //------------------------------------------------------------------
    // PARAMÈTRES DE LA FENÊTRE DU LOGICIEL
    //------------------------------------------------------------------

    Scene scene = new Scene(racine, WIDTH_WINDOW, HEIGHT_WINDOW);

    scene.getStylesheets().add("/design/stylesheet.css");

    stage.setScene(scene);
    //met la fenêtre au max
    stage.setMaximized(true);
    stage.setTitle("MCR - JEU DE LA MUERTA");
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.show();

  }

  /**
   * @return les informations du player 1 (gauche)
   */
  private VBox getPlayer1Informations() {
    VBox panneauVerticalGauche = new VBox();

    //On l'affiche
    panneauVerticalGauche.getStyleClass().add("menuLabelsGauche-vbox");

    //On créé le titre "labels"
    Label titrePanneauLabeal = new Label("Labels");

    //On le stylise grâce à la feuille CSS
    titrePanneauLabeal.getStyleClass().add("titre-label");

    //On l'affiche
    panneauVerticalGauche.getChildren().add(titrePanneauLabeal);

    //On initialise la liste de labels liés à l'image
    actionPlayer1Labels = new ListView();

    //On fait en sorte qu'ils soient cliquables
    actionPlayer1Labels.setCellFactory(TextFieldListCell.forListView());
    actionPlayer1Labels.setEditable(true);

    //On l'affiche et on le stylise
    panneauVerticalGauche.getChildren().add(actionPlayer1Labels);
    actionPlayer1Labels.getStyleClass().add("actionPlayer");

    //Permet de sélectionner plusieurs labels
    actionPlayer1Labels.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    //----------------------------------------------------
    // DELETE LABEL BUTTON
    //----------------------------------------------------
    deleteActionLabel = new Label("");
    Button deleteLabelButton = new Button();
    panneauVerticalGauche.getChildren().add(deleteLabelButton); //permet d'afficher l'élément dans le panneau
    panneauVerticalGauche.getChildren().add(deleteActionLabel); //indique l'état de l'ajout d'un label

    Image deleteIcon = new Image(getClass().getResourceAsStream("/images/delete.png"));
    ImageView deleteIconView = new ImageView(deleteIcon);
    deleteIconView.setFitHeight(15);
    deleteIconView.setFitWidth(15);
    deleteLabelButton.setGraphic(deleteIconView);//setting icon to button
    deleteLabelButton.getStyleClass().add("left-button");

    //On créé un event lié à l'action de la suppression du label
    deleteLabelButton.setOnAction(e ->
    {
      try {

        //Permet de supprimer plusieurs éléments en même temps
        if(actionPlayer1Labels.getSelectionModel().getSelectedItems().size() > 1)
        {
          int taille = actionPlayer1Labels.getSelectionModel().getSelectedItems().size();
          while (taille > 0)
          {
            actionPlayer1Labels.getItems().remove(actionPlayer1Labels.getSelectionModel().getSelectedItem());
            --taille;
          }
          deleteActionLabel.setText("Les actions ont été correctement supprimées.");
        }
        //condition pour un unique élément sélectionné
        else
        {
          actionPlayer1Labels.getItems().remove(actionPlayer1Labels.getSelectionModel().getSelectedItem());
          deleteActionLabel.setText("L'action a été correctement supprimée.");
        }

      } catch (Exception ex) {
        deleteActionLabel.setText("Aucune action sélectionnée.");
      }
    });

    //marges extérieures des deux cases + buttons
    VBox.setMargin(actionPlayer1Labels, new Insets(10, 10, 10, 10));
    VBox.setMargin(deleteActionLabel, new Insets(1, 10, 10, 10));
    VBox.setMargin(deleteLabelButton, new Insets(1, 10, 1, 200));

    return panneauVerticalGauche;
  }

  private StackPane corpsLogiciel() {
    sp = new StackPane();
    sp.getStyleClass().add("corps-gridPane");

    return sp;
  }

  /**
   * @return la barre de navigation contenant les différents boutons gérant la partie.
   */
  private HBox navBar() {
    //On créé une boxe horizontale qui définira l'espace "navigation".
    HBox navigation = new HBox(10);

    //on règle l'écart du contenu intérieur avec les bords de la boxe
    navigation.setPadding(new Insets(15, 15, 15, 15));
    navigation.setPrefWidth(WIDTH_WINDOW);

    //Espace entre les éléments
    navigation.setSpacing(10);

    //On lui applique d'autres styles présents dans la feuille CSS
    navigation.getStyleClass().add("header-hbox");

    //on ajoute les éléments à la boxe
    navigation.getChildren().addAll(validateTourButton, abandonTourButton);

    //On définit le bouton validerTour
    validateTourButton.setOnAction(
            actionEvent -> {
              //blablabla définir ce que fait le bouton "valider tour" ici.
            }
    );

    abandonTourButton.setOnAction(
            actionEvent -> {
              //blabla définir ce que fait le bouton "abandonner" ici.
            }
    );

    //----------------------------------------------------
    // SEPARATEUR - séparer les utility buttons sur la droite
    // utility buttons : minimize, quit.
    //----------------------------------------------------
    final Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    navigation.getChildren().add(spacer);

    //----------------------------------------------------
    // MINIMIZE BUTTON
    //----------------------------------------------------

    Image minimizeIcon = new Image(getClass().getResourceAsStream("/images/minimize.png"));
    ImageView minimizeIconView = new ImageView(minimizeIcon);
    minimizeIconView.setFitHeight(21);
    minimizeIconView.setFitWidth(21);
    minimizeButton.setGraphic(minimizeIconView);//setting icon to button
    minimizeButton.setAlignment(Pos.CENTER_RIGHT);

    minimizeButton.setOnAction(event -> {
      Stage stage = (Stage) minimizeButton.getScene().getWindow();
      stage.setIconified(true);

    });

    navigation.getChildren().add(minimizeButton);

    //----------------------------------------------------
    // QUIT BUTTON
    //----------------------------------------------------
    Image quitIcon = new Image(getClass().getResourceAsStream("/images/quit.png"));
    ImageView quitIconView = new ImageView(quitIcon);
    quitIconView.setFitHeight(21);
    quitIconView.setFitWidth(21);
    quitButton.setGraphic(quitIconView);//setting icon to button
    quitButton.setAlignment(Pos.CENTER_RIGHT);

    //On créé un event lié à la fermeture du logiciel.
    //Si on quitte sans avoir exporté notre travail, cela affiche une alerte.
    quitButton.setOnAction(event ->
    {
      //si le jeu est terminé, on permet de quitter simplement la fenêtre
      if (gameOver) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        //sinon on affiche un message d'alerte -> redemande.
      } else {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter l'application");
        alert.setHeaderText("");
        alert.setContentText("Vous êtes en pleine partie, voulez-vous réellement quitter le jeu ?");

        //enlève les boutons de base de la fenêtre.
        alert.initStyle(StageStyle.UNDECORATED);
        ButtonType oui = new ButtonType("Quitter");
        ButtonType non = new ButtonType("Annuler");

        alert.getButtonTypes().setAll(oui, non);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/design/stylesheet.css").toExternalForm());

        alert.showAndWait();
        if (alert.getResult() == oui) {
          Stage stage = (Stage) quitButton.getScene().getWindow();
          stage.close();
        }
      }
    });
    navigation.getChildren().add(quitButton);

    return navigation;
  }

  /**
   * Footer
   * Il s'agit de la fonction initialisant tout ce qui est inhérent au footer.
   * Dans ce footer, nous affichons les cartes du joueur.
   * @return le footer
   */
  private HBox footerBar() {

    //On définit une boxe horizontale qui définira l'espace "footer" -> cartes du joueur
    HBox footerCardsPlayer = new HBox();
    footerCardsPlayer.setPadding(new Insets(15, 15, 15, 15));
    footerCardsPlayer.getStyleClass().add("footer-header-hbox");

    //À REMPLACER PAR LES CARTES-----------
    Label blabla = new Label();
    blabla.setText("Blablabla test");
    //-------------------------------------
    footerCardsPlayer.getChildren().add(blabla);

    return footerCardsPlayer;
  }

  //----------------------------------------OLDIES----------------------------------
  //DON'T TOUCH, ON EN AURA P-E BESOIN APRES
  /*
  private static Board board;
  private LinkedList<Line> lines;
  private Group root;
  private Stage primaryStage;
  private Scene scene;

  public GameBoard() throws IOException {
    root = new Group();
    board = new Board(root);
    primaryStage = new Stage();
    initGame();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    scene = new Scene(root, Color.BLACK);
    primaryStage.setFullScreen(true);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void initGame() throws IOException {

  }

  */
}
