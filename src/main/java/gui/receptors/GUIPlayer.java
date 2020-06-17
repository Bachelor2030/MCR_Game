package gui.receptors;

import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
import javafx.scene.image.Image;
import network.states.ClientSharedState;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GUIPlayer extends GUIReceptor {
  private ClientSharedState clientSharedState;
  private ArrayList<GUICard> hand = new ArrayList<>();

  public GUIPlayer(
      String name, String imagePath, ArrayList<GUICard> hand, ClientSharedState clientSharedState) {
    super(name, imagePath);
    this.imagePath = imagePath;
    this.hand.addAll(hand);
    this.clientSharedState = clientSharedState;
  }

  /**
   * Permet de construire un joueur selon les données fournies.
   *
   * @param player : le joueur
   * @throws FileNotFoundException
   */
  public GUIPlayer(Player player, ClientSharedState clientSharedState)
      throws FileNotFoundException {
    super(player.getName(), player.getImgPath());
    initializeCards(player.getHand());
    this.clientSharedState = clientSharedState;
  }

  public GUIPlayer() {
    super("", "");
  }

  public ClientSharedState getClientSharedState() {
    return clientSharedState;
  }

  public void setClientSharedState(ClientSharedState clientSharedState) {
    this.clientSharedState = clientSharedState;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Permet de convertir un deck ArrayList<Card> en ArrayList<GUICard>
   *
   * @param deck : le deck contenant des cartes de type Card.
   * @throws FileNotFoundException
   */
  private void initializeCards(ArrayList<Card> deck) throws FileNotFoundException {
    for (Card card : deck) {
      this.hand.add(toGUICard(card));
    }
  }

  /**
   * Permet de créer une GUICard à partir d'une Card.
   *
   * @param card : carte de type Card.
   * @return une GUICard générée à partir d'une Card.
   * @throws FileNotFoundException
   */
  private GUICard toGUICard(Card card) throws FileNotFoundException {
    return new GUICard(
        card.getID(), card.getName(), card.getType(), card.getCost(), clientSharedState);
  }

  public Image getImage() {
    try {
      return new Image(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return new Image(imagePath);
  }

  public void addHand(ArrayList<GUICard> newHand) {
    hand.addAll(newHand);
  }

  public void setImgPath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void addToHand(GUICard card) {
    hand.add(card);
  }

  public void removeFromHand(int cardID) {
    for (GUICard card : hand) {
      if (card.getId() == cardID) {
        hand.remove(card);
        break;
      }
    }
  }
}
