package gui.receptors;

import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
import gui.GUICard;
import javafx.scene.image.Image;
import network.states.ClientSharedState;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GUIPlayer {
    private ClientSharedState clientSharedState;
    private String name;
    private String imagePath;
    private ArrayList<GUICard> hand = new ArrayList<>();

    public GUIPlayer(String name, String imagePath, ArrayList<GUICard> hand, ClientSharedState clientSharedState) {
        this.name = name;
        this.imagePath = imagePath;
        this.hand.addAll(hand);
        this.clientSharedState = clientSharedState;
    }

    /**
     * Permet de construire un joueur selon les données fournies.
     * @param player : le joueur
     * @throws FileNotFoundException
     */
    public GUIPlayer(Player player, ClientSharedState clientSharedState) throws FileNotFoundException {
        name = player.getName();
        imagePath = player.getImgPath();
        initializeCards(player.getHand());
        this.clientSharedState = clientSharedState;
    }

    public GUIPlayer() {}

    public void setClientSharedState(ClientSharedState clientSharedState) {
        this.clientSharedState = clientSharedState;
    }

    public ClientSharedState getClientSharedState() {
        return clientSharedState;
    }

    public String getName() {
        return name;
    }

    /**
     * Permet de convertir un deck ArrayList<Card> en ArrayList<GUICard>
     * @param deck : le deck contenant des cartes de type Card.
     * @throws FileNotFoundException
     */
    private void initializeCards(ArrayList<Card> deck) throws FileNotFoundException {
        for(Card card : deck) {
            this.hand.add(toGUICard(card));
        }
    }

    /**
     * Permet de créer une GUICard à partir d'une Card.
     * @param card : carte de type Card.
     * @return une GUICard générée à partir d'une Card.
     * @throws FileNotFoundException
     */
    private GUICard toGUICard(Card card) throws FileNotFoundException {
        return new GUICard(card.getID(), card.getName(), card.getType(), card.getCost(), clientSharedState);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }
}
