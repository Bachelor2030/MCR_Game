package gui.receptors;

import gameLogic.invocator.card.CardType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import network.Messages;
import network.states.ClientSharedState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;

import static network.utilities.JsonClient.jsonType;

public class GUICard {
    private final int id;         // the ID of the card
    private String name;          // the name of the card
    private String description;   // the description of the card
    private int cost;             // the cost (in action points)
    private CardType type;        //type de la carte
    private ImageView view;
    private ToggleButton button;

  private ClientSharedState clientSharedState;

  private final String SPELL_PATH = "src/main/resources/design/images/cards/spell.png",
      CREATURE_PATH = "src/main/resources/design/images/cards/creature.png",
      TRAP_PATH = "src/main/resources/design/images/cards/trap.png",
      ERROR_PATH = "src/main/resources/design/images/cards/cardSample.png";

    public GUICard(int id, String name, CardType type, int cost, String description) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.description = description;
    }

    public GUICard(int id, String name, CardType type, int cost, ClientSharedState clientSharedState) throws FileNotFoundException {
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
        Tooltip t = new Tooltip(
                        "- Informations -\n\nType : " + type + "\n" + name + "\n" + "PA : " + cost + "\n");
        button.setTooltip(t);
    }
  private String definePictureAccordingToType() {
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

    public int getId() {
        return id;
    }

  public String getName() {
    return name;
  }

  public int getCost() {
    return cost;
  }

  public CardType getType() {
    return type;
  }

  public ImageView getView() {
    return view;
  }

  public ToggleButton getButton() {
    return button;
  }
}
