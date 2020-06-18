package gui.receptors;

import javafx.scene.image.Image;
import network.states.ClientSharedState;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Représentation d'un player pour la GUI
 */
public class GUIPlayer extends GUIReceptor {
  private ClientSharedState clientSharedState; //l'état partagé du client
  private ArrayList<GUICard> hand = new ArrayList<>(); //les cartes présentes dans la main du joueur

  /**
   * Constructeur de la classe
   * @param name : le nom du joueur
   * @param imagePath : l'image du joueur
   * @param hand : la main du joueur (cartes présentes dans sa main)
   * @param clientSharedState : l'état partagé du client
   */
  public GUIPlayer(
      String name, String imagePath, ArrayList<GUICard> hand, ClientSharedState clientSharedState) {
    super(name, imagePath);
    this.imagePath = imagePath;
    this.hand.addAll(hand);
    this.clientSharedState = clientSharedState;
  }

  /**
   * Initialise un player vide
   */
  public GUIPlayer() {
    super("", "");
  }

  /**
   * Permet de modifier l'était partage du client.
   * @param clientSharedState : l'état partagé du client
   */
  public void setClientSharedState(ClientSharedState clientSharedState) {
    this.clientSharedState = clientSharedState;
  }

  /**
   * @return l'état partagé du client.
   */
  public ClientSharedState getClientSharedState() {
    return clientSharedState;
  }

  /**
   * @return le nom du client.
   */
  public String getName() {
    return name;
  }

  /**
   * @return l'image liée au client
   */
  public Image getImage() {
    try {
      return new Image(new FileInputStream(imagePath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return new Image(imagePath);
  }

  /**
   * Permet d'ajouter une liste de cartes dans la main du joueur.
   * @param newHand : la liste de cartes à ajouter.
   */
  public void addHand(ArrayList<GUICard> newHand) {
    hand.addAll(newHand);
  }

  /**
   * Permet de modifier l'imagePath.
   * @param imagePath : le nouveau chemin de l'image.
   */
  public void setImgPath(String imagePath) {
    this.imagePath = imagePath;
  }

  /**
   * Permet de modifier le nom du joueur.
   * @param name : le nom de remplacement.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return l'image path du joueur.
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * Permet d'ajouter une carte à la main du joueur.
   * (utile pour chaque début de tour -> 1 carte s'ajoute !)
   * @param card : la carte à ajouter.
   */
  public void addToHand(GUICard card) {
    hand.add(card);
  }

  /**
   * Supprime une carte spécifique dans la main du joueur.
   * (utile lorsqu'une carte a été jouée)
   * @param cardID : l'id de la carte
   */
  public void removeFromHand(int cardID) {
    for (GUICard card : hand) {
      if (card.getId() == cardID) {
        hand.remove(card);
        break;
      }
    }
  }

  /**
   * @return les cartes présentes dans la main du joueur.
   */
  public ArrayList<GUICard> getHand() {
    return hand;
  }
}
