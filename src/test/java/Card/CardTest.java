package Card;

import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    private static Card card;
    private static final String NAME = "An awesome card";
    private static final CardType TYPE = CardType.CREATURE;
    private static final int COST = 13;

    @BeforeAll
    static void init() {
        card = new Card(NAME, TYPE, COST);
    }

    @Test
    public void CardShouldHaveTheCorrectNameAndType() {
        assertEquals(TYPE.toString() + " " + NAME, card.getName());
    }

    @Test
    public void CardShouldHaveTheCorrectCost() {
        assertEquals(COST, card.getCost());
    }
}
