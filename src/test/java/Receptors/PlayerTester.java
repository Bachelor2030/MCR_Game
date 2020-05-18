package Receptors;

import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Common.Receptors.Chest;
import Common.Receptors.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlayerTester {
    private static Player player;
    private static String name;

    @BeforeAll
    static void initialise() {
        name = "George";
        LinkedList<Card> cards = new LinkedList<>();
        cards.add(new Card("Black Hole", CardType.SPELL, 2, null));
        cards.add(new Card("Furnace", CardType.SPELL, 2, null));
        cards.add(new Card("Bombe", CardType.TRAP, 3, null));
        cards.add(new Card("Black Hole", CardType.TRAP, 3, null));
        cards.add(new Card("Pier", CardType.CREATURE, 8, null));
        cards.add(new Card("Sebas-chan", CardType.CREATURE, 9, null));
        cards.add(new Card("Gregou", CardType.CREATURE, 10, null));

        player = new Player(name, cards);
        assertEquals(3, player.getNbrCardsInHand());
    }

    @Test
    public void PlayerShouldHaveTheCorrectName() {
        assertEquals(name, player.getName());
    }

    @Test
    public void HittingAPlayersChestMustDiminishTheirLifePoints() {
        player.hitChest(1,20);
        assertEquals(1, player.getNbChestsDestroyed());
        for (Chest chest : player.getChests()) {
            System.out.println(chest);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 15, 31})
    public void TheNumberOfActionPointMustBeCorrect(int turn) {
        player.playTurn(turn);
        assertEquals((turn <= 15 ? turn : 15), player.getActionPoints());
    }

    @Test
    public void IfPlaayersHandIsFullTheDrwanCardIsDiscarded() {
        player.playTurn(2);

        int n = player.getNbrCardsInHand();
        player.playTurn(3);

        assertEquals(n+1, player.getNbrCardsInHand());

        player.playTurn(4);
        player.playTurn(5);
        player.playTurn(6);

        n = player.getNbrCardsInHand();
        player.playTurn(7);

        assertEquals(n, player.getNbrCardsInHand());
    }

    @Test
    public void PlayerShouldHaveTheCorrectAmountOfChests() {
        for (Chest chest : player.getChests()) {
            System.out.println(chest);
        }
        assertEquals(Player.getStartingNbrChests(), player.getNbChests());
    }
}
