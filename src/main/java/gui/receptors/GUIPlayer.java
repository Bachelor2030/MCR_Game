package gui.receptors;

import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
import gui.GUICard;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GUIPlayer {
    private String name;
    private String imagePath;
    private ArrayList<GUICard> deck;

    /**
     * Permet de construire un joueur selon les données fournies.
     * @param player : le joueur
     * @throws FileNotFoundException
     */
    public GUIPlayer(Player player) throws FileNotFoundException {
        name = player.getName();
        imagePath = player.getImgPath();
        initializeCards(player.getHand());
    }

    /**
     * Permet de convertir un deck ArrayList<Card> en ArrayList<GUICard>
     * @param deck : le deck contenant des cartes de type Card.
     * @throws FileNotFoundException
     */
    private void initializeCards(ArrayList<Card> deck) throws FileNotFoundException {
        for(Card card : deck) {
            this.deck.add(toGUICard(card));
        }
    }

    /**
     * Permet de créer une GUICard à partir d'une Card.
     * @param card : carte de type Card.
     * @return une GUICard générée à partir d'une Card.
     * @throws FileNotFoundException
     */
    private GUICard toGUICard(Card card) throws FileNotFoundException {
        return new GUICard(card.getID(), card.getName(), card.getType(), card.getCost());
    }
}
