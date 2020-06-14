package GUI;

import javafx.scene.control.Button;

public class GameButton {
    private Button button;

    public GameButton(String name, String styleClass){
        button = new Button(name);
        button.getStyleClass().add(styleClass);
    }

    public Button getButton() {
        return button;
    }
}
