package gui;

import gameLogic.invocator.card.CardType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GUICard {
    private final int id;       // the ID of the card
    private String name;        // the name of the card
    private int cost;           // the cost (in action points)
    private CardType type;      //type de la carte
    private ImageView view;
    private Button button;

    private final String
            SPELL_PATH    = "src/main/resources/design/images/cards/spell.png",
            CREATURE_PATH = "src/main/resources/design/images/cards/creature.png",
            TRAP_PATH     = "src/main/resources/design/images/cards/trap.png",
            ERROR_PATH    = "src/main/resources/design/images/cards/cardSample.png";

    public GUICard(int id, String name, CardType type, int cost) throws FileNotFoundException {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.type = type;
        Image image = new Image(new FileInputStream(definePictureAccordingToType()));
        view = new ImageView(image);
        view.setFitWidth(image.getWidth() * 0.35);
        view.setFitHeight(image.getHeight() * 0.35);
        button = new Button();
        button.getStyleClass().add("button-island");
        button.setOnAction(
                actionEvent -> {
                    System.out.println("j'appuye sur une carte");
                });
        button.setGraphic(view);
    }

    private String definePictureAccordingToType() {
        switch (type) {
            case TRAP:
                return TRAP_PATH;
            case CREATURE:
                return CREATURE_PATH;
            case SPELL:
                return SPELL_PATH;
            default :
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

    public Button getButton() {
        return button;
    }
}
