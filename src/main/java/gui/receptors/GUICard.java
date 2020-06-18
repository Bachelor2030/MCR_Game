package gui.receptors;

import gameLogic.invocator.card.CardType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import network.Messages;
import network.states.ClientSharedState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static network.utilities.JsonClient.jsonType;

/**
 * Représentation d'une carte pour la GUI
 */
public class GUICard {
  private final int id; // the ID of the card
  private final String name; // the name of the card
  private final int cost; // the cost (in action points)
  private final CardType type; // type de la carte
  private ImageView view; //la view liée à l'image
  private ToggleButton button; //le button lié à la carte (pour pouvoir appuyer dessus)
  private ClientSharedState clientSharedState; //l'état partagé du client

  /**
   * Constructeur de la classe.
   * @param id : l'id de la carte
   * @param name : le nom de la carte
   * @param type : le type de la carte (spell, trap, creature)
   * @param cost : le coût (point d'action) de la carte
   * @param description : la description de la carte
   */
  public GUICard(int id, String name, CardType type, int cost, String description) {
    this.id = id;
    this.name = name;
    this.cost = cost;
    this.type = type;
    // the description of the card
  }

  /**
   * Constructeur de la classe
   * @param id : l'id de la carte
   * @param name : le nom de la carte
   * @param type : le type de la carte (spell, trap, creature)
   * @param cost : le coût (point d'action) de la carte
   * @param clientSharedState : l'état partagé du client
   * @throws FileNotFoundException
   */
  public GUICard(int id, String name, CardType type, int cost, ClientSharedState clientSharedState)
      throws FileNotFoundException {
    this.id = id;
    this.name = name;
    this.cost = cost;
    this.type = type;
    this.clientSharedState = clientSharedState;
    Image image = new Image(new FileInputStream(definePictureAccordingToType()));
    view = new ImageView(image);
    view.setFitWidth(image.getWidth() * 0.35);
    view.setFitHeight(image.getHeight() * 0.35);
    button = new ToggleButton();
    button.getStyleClass().add("toggle-unselected");
    button.setGraphic(view);

    // informations de la carte dans une bulle
    Tooltip t =
        new Tooltip(
            "- Informations -\n\nType : " + type + "\n" + name + "\n" + "PA : " + cost + "\n");
    button.setTooltip(t);
  }

  private String definePictureAccordingToType() {
    String SPELL_PATH = "src/main/resources/design/images/cards/spell.png";
    String CREATURE_PATH = "src/main/resources/design/images/cards/creature.png";
    String TRAP_PATH = "src/main/resources/design/images/cards/trap.png";
    String ERROR_PATH = "src/main/resources/design/images/cards/cardSample.png";
    switch (type) {
      case TRAP:
        return TRAP_PATH;
      case CREATURE:
        return CREATURE_PATH;
      case SPELL:
        return SPELL_PATH;
      default:
        return ERROR_PATH;
    }
  }

  /**
   * Permet de récupérer le JSON lié à la carte
   * @return les informations de la carte sous forme de JSON
   * @throws JSONException
   */
  public JSONObject getJson() throws JSONException {
    JSONObject play = jsonType(Messages.JSON_TYPE_PLAY);
    play.put(Messages.JSON_TYPE_CARD_ID, clientSharedState.getSelectedCard().getId());

    JSONObject position = new JSONObject();
    position.put(Messages.JSON_TYPE_LINE, clientSharedState.getChosenPosition().getKey());
    position.put(Messages.JSON_TYPE_SPOT, clientSharedState.getChosenPosition().getValue());
    play.put(Messages.JSON_TYPE_POSITION, position);

    clientSharedState.setChosenPosition(null);

    return play;
  }

  /**
   * @return l'id de la carte
   */
  public int getId() {
    return id;
  }

  /**
   * @return le nom de la carte
   */
  public String getName() {
    return name;
  }

  /**
   * @return le coût de la carte
   */
  public int getCost() {
    return cost;
  }

  /**
   * @return le type de la carte (spell, trap, creature)
   */
  public CardType getType() {
    return type;
  }

  /**
   * @return le bouton lié à la carte
   */
  public ToggleButton getButton() {
    return button;
  }
}
