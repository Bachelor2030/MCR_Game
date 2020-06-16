package gui;

import gameLogic.invocator.card.CardType;
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

    private final String
            SPELL_PATH    = "src/main/resources/design/images/cards/spell.png",
            CREATURE_PATH = "src/main/resources/design/images/cards/creature.png",
            TRAP_PATH     = "src/main/resources/design/images/cards/trap.png";

    public GUICard(int id, String name, CardType type, int cost) throws FileNotFoundException {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.type = type;
        FileInputStream imagePath = new FileInputStream(definePictureAccordingToType());
        Image image = new Image(imagePath);
        view = new ImageView(image);
        view.setVisible(true);
    }

    private String definePictureAccordingToType() {
        switch (type) {
            case TRAP: return TRAP_PATH;
            case CREATURE: return CREATURE_PATH;
            default : return SPELL_PATH;
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

    public ImageView getView() throws FileNotFoundException {
        return new ImageView(new Image(new FileInputStream(definePictureAccordingToType())));
    }
}
