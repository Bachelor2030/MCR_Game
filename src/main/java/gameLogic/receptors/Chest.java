package gameLogic.receptors;

import gameLogic.commands.playersAction.PlayersAction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import network.states.ServerSharedState;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/** Chest owned by a Player. Said Player must protect it by using creatures and spells */
public class Chest extends LiveReceptor {
  // Max life points for a chest
  private static final int CHEST_LIFE_POINTS = 5;
  private String imagePath = "src/main/resources/design/images/treasure.png";
  private Image image;
  private ImageView imageView;

  /**
   * Create a chest with the given name and owner
   *
   * @param name the name of the current chest
   * @param owner the Player that owns the current chest
   */
  public Chest(String name, Player owner, ServerSharedState serverSharedState) {
    super(name, CHEST_LIFE_POINTS, "Chest", serverSharedState);
    super.setOwner(owner);
    initDisplayChest();
  }

  private void initDisplayChest() {
    try {
      image = new Image(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    imageView = new ImageView(image);
    imageView.setFitWidth(image.getWidth() * 0.12);
    imageView.setFitHeight(image.getHeight() * 0.12);
  }

  /**
   * Checks if the current chest is closed A chest is opened if it has no lifePoints left
   *
   * @return true if the chest still has life points left
   */
  public boolean isClosed() {
    return isAlive();
  }

  @Override
  public void playTurn(int turn, PlayersAction action) {}
}
