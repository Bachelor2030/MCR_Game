package gui;

import gameLogic.invocator.card.CardType;

public class GUICard {
    private final int id;             // the ID of the card
    private String name;        // the name of the card
    private int cost;           // the cost (in action points)
    private CardType type;      //type de la carte

    public GUICard(int id, String name, CardType type, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.type = type;
    }
}
