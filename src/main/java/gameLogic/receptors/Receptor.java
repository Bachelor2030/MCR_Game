package gameLogic.receptors;

import gameLogic.commands.ConcreteCommand;
import gameLogic.commands.Macro;
import gameLogic.commands.playersAction.PlayersAction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import network.Messages;
import network.states.ServerSharedState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Modélise la classe recevant les commandes
 */
public abstract class Receptor {
  protected Macro lastMove;
  protected String name;

  private ServerSharedState serverSharedState;

  private String imgPath = "src/main/resources/design/images/creatures/empty.jpg";
  private FileInputStream fis;

  private Image image;
  private ImageView imageView;

  public Receptor(String name, ServerSharedState serverSharedState) {
    this.name = name;
    this.serverSharedState = serverSharedState;
    lastMove = new Macro(new ArrayList<>(), serverSharedState);
    initDisplay();
  }

  public Receptor() {
    this.name = "empty";
    initDisplay();
  }

  /**
   * Permet de récupérer le nom du recepteur
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Permet de récupérer la dernière action faite pa le recepteur
   * @return
   */
  public Macro getLastMove() {
    return lastMove;
  }

  /**
   * Permet de faire jouer son tour au recepteur courrant
   * @param turn le numéro du tour à jouer
   * @param action l'action à executer pour le tour donné
   */
  public abstract void playTurn(int turn, PlayersAction action);

  /**
   * Permet d'annuler la dernière action (Commande) faite sur le recepteur
   */
  public void undoLastMove() {
    lastMove.undo(lastMove.getReceptor());
  }

  /**
   * Permet de réexecuter la dernière action faite sur le recepteur
   */
  public void redoLastMove() {
    lastMove.execute(lastMove.getReceptor());
  }

  @Override
  public String toString() {
    return name;
  }

  /**
   * Permet d'initialiser l'affichage
   */
  private void initDisplay() {
    try {
      image = new Image(new FileInputStream(imgPath));
      imageView = new ImageView(image);
      imageView.setFitWidth(image.getWidth() * 0.2);
      imageView.setFitHeight(image.getHeight() * 0.2);
    } catch (Exception e) {
      // System.out.println(e);
    }
  }

  public ServerSharedState getServerSharedState() {
    return serverSharedState;
  }

  public Image getImage() {
    return image;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
    initDisplay();
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setTo(Receptor receptor) {
    System.out.println("I am a new receptor");
    if (receptor == null) {
      try {
        fis = new FileInputStream("src/main/resources/design/images/creatures/empty.jpg");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      initDisplay();
    } else {
      fis = receptor.fis;
      image = receptor.image;
      imageView = receptor.imageView;
    }
  }

  public JSONObject toJson() {
    JSONObject receptor = new JSONObject();
    try {
      receptor.put(Messages.JSON_TYPE_NAME, name);
      receptor.put(Messages.JSON_TYPE_IMAGE, imgPath);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return receptor;
  }

  public void addLastMove(ConcreteCommand concreteCommand) {
    lastMove.addCommand(concreteCommand);
  }

  public void removeLastMove(ConcreteCommand concreteCommand) {
    lastMove.getCommands().remove(concreteCommand);
  }

  public void addLastMoves(ArrayList<ConcreteCommand> commands) {
    lastMove.getCommands().addAll(commands);
  }
}
