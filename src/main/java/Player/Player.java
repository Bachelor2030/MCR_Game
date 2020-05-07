package Player;

import ModelClasses.Receptor;

public class Player extends Receptor {
    private static final int NBR_CARDS_PER_DECK = 50;
    private static final int NBR_CARDS_MAX_IN_HAND = 10;

    private Card[] deck = new Card[NBR_CARDS_PER_DECK];
    private Card[] hand = new Card[NBR_CARDS_MAX_IN_HAND];

    public Player(String name, int lifePoints, Card[] deck) {
        super(name, lifePoints);

        for (int card = 0; card < NBR_CARDS_PER_DECK; ++card) {
            this.deck[card] = deck[card];
        }
    }

    @Override
    public void action() {

    }

    @Override
    public void playTurn() {

    }
}
