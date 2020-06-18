package gui.board;

import gui.receptors.GUIReceptor;
import gui.receptors.GUITrap;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import network.states.ClientSharedState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/** Cette classe représente une case qui constitue une ligne de combat */
public class GUISpot {

  // un case est représentée par un numéro.
  private final int number;

  //La ligne sur laquelle se trouve le spot (l'île).
  private final int lineNumber;

  // Compteur de case.
  private static int spotCounter = 0;

  //L'état partagé du client.
  private ClientSharedState clientSharedState;

  // l'éventuelle créature présente sur la case.
  private GUIReceptor occupant;

  // permet de savoir si une île est piégée.
  private boolean isTrapped;

  // l'image représentant le spot
  private FileInputStream imagePath =
      new FileInputStream("src/main/resources/design/images/field/island.png");
  Image image;
  ImageView imageView;
  Button button; //Permet de cliquer sur un emplacement (île).

  /**
   * Permet de construire un îlot
   *
   * @throws IOException
   */
  public GUISpot(int line, ClientSharedState clientSharedState) throws IOException {
    this(
        spotCounter++, line, clientSharedState);
  }

  /**
   * Permet de construire un ilôt avec une position et un nombre le définissant
   *
   * @param number : allant de 0 à 9
   * @throws FileNotFoundException
   */
  private GUISpot(int number, int line, ClientSharedState clientSharedState)
      throws FileNotFoundException {
    image = new Image(imagePath);
    imageView = new ImageView(image);
    this.clientSharedState = clientSharedState;
    isTrapped = false;
    button = new Button();
    button.getStyleClass().add("button-island"); //permet d'appliquer un style CSS au bouton.
    button.setOnAction(
        actionEvent -> {
          if (!clientSharedState.isMyTurn()) {
            // on créé une alerte WARNING qui indique à l'utilisateur
            // que ce n'est pas à lui de jouer.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("Ce n'est pas à votre tour de jouer !");
            Image image = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Emojione_1F62D.svg/64px-Emojione_1F62D.svg.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane
                    .getStylesheets()
                    .add(getClass().getResource("/design/css/styleSheet.css").toExternalForm());
            alert.show();

            System.out.println("Please wait for your turn");
            return;
          } else {
            // If there is a selected card and it's not an empty card
            if (clientSharedState.getSelectedCard() != null) {
              clientSharedState.setChosenPosition(
                  new Pair<>(line, number % GUILine.getNbSpots()));
              try {
                JSONObject json = clientSharedState.getSelectedCard().getJson();
                clientSharedState.pushJsonToSend(json);
                clientSharedState.setIntendToSendJson(true);
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          }
        });
    button.setGraphic(imageView);
    this.number = number % (GUILine.getNbSpots());
    this.lineNumber = line;
    initDisplaySpot();
  }

  /**
   * Permet d'initialiser correctement la place d'un îlot
   */
  private void initDisplaySpot() {
    float MIN_WIDTH_RATIO = 0.6f;
    imageView.setFitWidth(image.getWidth() * MIN_WIDTH_RATIO);
    imageView.setFitHeight(image.getHeight() * MIN_WIDTH_RATIO);
  }

  /**
   * Permet de savoir si une case est occupée par une créature.
   *
   * @return true si occupée, false sinon.
   */
  public boolean isEmpty() {
    return occupant == null || occupant.getName().equals("empty") || occupant.getClass() == GUITrap.class;
  }

  /**
   * Permet de set l'éventuelle créature présente sur la case.
   *
   * @param occupant : la créature
   */
  public void setOccupant(GUIReceptor occupant) {
    this.occupant = occupant;
  }

  /** Modélise le départ d'une créature de la case. */
  public void leave() {
    this.occupant = null;
  }

  /**
   * Permet de récupérer l'éventuelle créature présente sur la case.
   *
   * @return la créature, si elle occupe la case. Sinon null.
   */
  public GUIReceptor getOccupant() {
    return occupant;
  }

  /** @return l'image d'un spot (ici une petite île) */
  public FileInputStream getImagePath() {
    return imagePath;
  }

  /**
   * Récupère la vue d'un spot.
   * @return la vue sous forme d'ImageView d'un spot (île).
   */
  public ImageView getImageView() {
    return imageView;
  }

  /**
   * Permet de récupérer le bouton d'un emplacement.
   * @return le bouton lié à l'emplacement.
   */
  public Button getButton() {
    return button;
  }

  /**
   * Permet de savoir si un emplacement est piégé.
   * @return true si oui, false sinon.
   */
  public boolean isTrapped() {
    return isTrapped;
  }

  /**
   * Permet de renseigner qu'un emplacement est à présent piégé.
   */
  public void trap() {
    isTrapped = true;
  }

  /**
   * Permet de renseigner qu'un emplacement n'est plus piégé.
   */
  public void unTrap() {
    isTrapped = false;
  }


  public JSONObject toJson() {
    // TODO
    return null;
  }

  /**
   * Retourne le numéro de ligne (0 à NB_LINES-1) où se trouve le spot
   * @return la ligne qui contient le spot.
   */
  public int getLineNumber() {
    return lineNumber;
  }

  /**
   * Permet de récuéprer l'indice du spot.
   * @return l'indice du spot (0 à NB_SPOTS)
   */
  public int getSpotNumber() {
    return (number % GUILine.getNbSpots());
  }
}
