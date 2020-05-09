package Player;

import Card.Card;
import Card.TrapCard;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTester {
    private static Player player;
    private static String name;

    @BeforeAll
    static void initialise() {
        name = "A name";
        LinkedList<Card> cards = new LinkedList<>();
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());
        cards.add(new TrapCard());

        player = new Player(name, cards);
    }

    @Test
    public void PlayerShouldHaveTheCorrectName() {
        assertEquals(name, player.getName());
    }

    @Test
    public void HittingAPlayerMustDiminishTheirLifePoints() {
        player.hit(20);
        assertEquals(30, player.getLifePoints());
    }

    @Test
    public void PlayerShouldStartWithTheCorrectAmountOfCardsInHand() {
        assertEquals(3, player.getNbrCardsInHand());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 15, 31})
    public void TheNumberOfActionPointMustBeCorrect(int turn) {
        player.playTurn(turn);
        assertEquals((turn <= 15 ? turn : 15), player.getActionPoints());
    }
}
