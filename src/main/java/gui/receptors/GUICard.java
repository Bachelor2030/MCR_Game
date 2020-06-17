package gui.receptors;

import gameLogic.invocator.card.CardType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import network.states.ClientSharedState;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GUICard {
  private final int id; // the ID of the card
  private final String SPELL_PATH = "src/main/resources/design/images/cards/spell.png",
      CREATURE_PATH = "src/main/resources/design/images/cards/creature.png",
      TRAP_PATH = "src/main/resources/design/images/cards/trap.png",
      ERROR_PATH = "src/main/resources/design/images/cards/cardSample.png";
  private String name; // the name of the card
  private String description; // the description of the card
  private int cost; // the cost (in action points)
  private CardType type; // type de la carte
  private ImageView view;
  private ToggleButton button;
  private ClientSharedState clientSharedState;

  public GUICard(int id, String name, CardType type, int cost, String description) {
    this.id = id;
    this.name = name;
    this.cost = cost;
    this.type = type;
    this.description = description;
  }

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
    button.setOnAction(
        actionEvent -> {
          if (!clientSharedState.isMyTurn()) {
            return;
          }

          if (clientSharedState.getSelectedCard() == null
              || clientSharedState.getSelectedCard().getName().equals("empty")) {
            clientSharedState.setSelectedCard(this);
          } else if (clientSharedState.getSelectedCard() == this) {
            try {
              clientSharedState.setSelectedCard(
                  new GUICard(0, "empty", CardType.SPELL, 0, clientSharedState));
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            }
          }
          System.out.println("Card clicked");
        });
    button.setGraphic(view);

    // informations de la carte dans une bulle
    Tooltip t =
        new Tooltip(
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
