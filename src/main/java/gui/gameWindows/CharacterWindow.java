package gui.gameWindows;

import gameLogic.receptors.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CharacterWindow extends GameWindow
{
  private final String
          DARK_DONINI_PATH = "src/main/resources/design/images/characters/darkDonini.png",
          ELODIE_PATH = "src/main/resources/design/images/characters/elodie.png",
          CLARUSSO_PATH = "src/main/resources/design/images/characters/clarisse.png",
          MATT_PATH = "src/main/resources/design/images/characters/matt.png",
          GUS_PATH = "src/main/resources/design/images/characters/gus.png";

  private VBox corps;

  private ToggleGroup radioButtonGroup;

  public CharacterWindow(BorderPane racine, HBox navigation, Stage stage,
                         final int WIDTH_WINDOW, Player player) throws FileNotFoundException {
    super(racine, navigation, false, stage);
    generate();
  }

  private void generate() throws FileNotFoundException {
    corps = new VBox();
    corps.getStyleClass().add("parameters-body");
    corps.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));
    Label title = new Label("Veuillez choisir un personnage :");
    title.getStyleClass().add("instructions-title");

    HBox characters = new HBox();
    radioButtonGroup = new ToggleGroup();

    RadioButton button2 = new RadioButton("select second");
    button2.setToggleGroup(radioButtonGroup);

    //DARK ASCII
    VBox darkDoniniBox = createToggle(radioButtonGroup, DARK_DONINI_PATH, "Dark Donini", true);
    darkDoniniBox.setAlignment(Pos.CENTER);
    darkDoniniBox.setSpacing(20);

    //ELODIE
    VBox elodieBox = createToggle(radioButtonGroup, ELODIE_PATH, "Elodie", false);
    elodieBox.setAlignment(Pos.CENTER);
    elodieBox.setSpacing(20);

    //CLARISSE
    VBox clarisseBox =  createToggle(radioButtonGroup, CLARUSSO_PATH, "Clarusso", false);
    clarisseBox.setAlignment(Pos.CENTER);
    clarisseBox.setSpacing(20);

    //GUS
    VBox gusBox =  createToggle(radioButtonGroup, GUS_PATH, "Gus", false);
    gusBox.setAlignment(Pos.CENTER);
    gusBox.setSpacing(20);

    //MATT
    VBox mattBox =  createToggle(radioButtonGroup, MATT_PATH, "Matt", false);
    mattBox.setAlignment(Pos.CENTER);
    mattBox.setSpacing(20);

    characters.getChildren().addAll(darkDoniniBox,elodieBox,clarisseBox, gusBox, mattBox);
    characters.setSpacing(70);
    characters.setAlignment(Pos.CENTER);
    corps.getChildren().addAll(title, characters);
    corps.setAlignment(Pos.CENTER);
    corps.setSpacing(70);

  }

  /**
   * Créé une sélection de personnage
   * @param group : le groupe de toggle
   * @param imgPath : le path de l'image
   * @param buttonName : le nom du bouton
   * @param isSelected : est-il sélectionné à la création ?
   * @return une VBox contenant l'image et le bouton alignés verticalement.
   * @throws FileNotFoundException
   */
  private VBox createToggle(ToggleGroup group, String imgPath, String buttonName, boolean isSelected) throws FileNotFoundException {
    VBox box = new VBox();
    Image image = new Image(new FileInputStream(imgPath));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(image.getWidth() * 0.5);
    imageView.setFitHeight(image.getHeight() * 0.5);

    RadioButton button = new RadioButton(buttonName);
    button.setToggleGroup(group);
    button.setSelected(isSelected);
    box.getChildren().addAll(imageView, button);

    return box;
  }

  public VBox getCorps() {
    return corps;
  }

  public ToggleGroup getRadioButtonGroup() {
    return radioButtonGroup;
  }

  public String defineSelectedUrl()
  {
    RadioButton selected = (RadioButton) radioButtonGroup.getSelectedToggle();
    switch(selected.getText())
    {
      case "Elodie"     : return ELODIE_PATH;
      case "Clarusso"   : return CLARUSSO_PATH;
      case "Gus"        : return GUS_PATH;
      case "Matt"       : return MATT_PATH;
      default : return DARK_DONINI_PATH;
    }
  }
}
