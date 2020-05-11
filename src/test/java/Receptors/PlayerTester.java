package Receptors;

import Card.Card;
import Card.CardType;
import ModelClasses.Receptors.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTester {
    private static Player player;
    private static String name;

    @BeforeAll
    static void initialise() {
        name = "A name";
        LinkedList<Card> cards = new LinkedList<>();
        cards.add(new Card("Black Hole", CardType.SPELL, 2));
        cards.add(new Card("Furnace", CardType.SPELL, 2));
        cards.add(new Card("Bombe", CardType.TRAP, 3));
        cards.add(new Card("Black Hole", CardType.TRAP, 3));
        cards.add(new Card("Pier", CardType.CREATURE, 8));
        cards.add(new Card("Sebas-chan", CardType.CREATURE, 9));
        cards.add(new Card("Gregou", CardType.CREATURE, 10));

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
