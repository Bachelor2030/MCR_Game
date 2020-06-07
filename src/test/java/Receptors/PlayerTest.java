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

public class PlayerTest {
    private static Player player;
    private static String name;

    @BeforeAll
    static void initialise() {
        name = "George";
        LinkedList<Card> cards = new LinkedList<>();
        cards.add(new Card(1, "Black Hole", CardType.SPELL, 0));
        cards.add(new Card(1, "Furnace", CardType.SPELL, 0));
        cards.add(new Card(1, "Bombe", CardType.TRAP, 0));
        cards.add(new Card(1, "Black Hole", CardType.TRAP, 0));
        cards.add(new Card(1, "Pier", CardType.CREATURE, 0));
        cards.add(new Card(1, "Sebas-chan", CardType.CREATURE, 0));
        cards.add(new Card(1, "Gregou", CardType.CREATURE, 0));

        player = new Player(name, cards);
    }

    @Test
    public void PlayerShouldHaveTheCorrectName() {
        assertEquals(name, player.getName());
    }

    @Test
    public void HittingAPlayersEggMustDiminishTheirLifePoints() {
        player.hitChest(1,20);
        assertEquals(1, player.getNbChestsDestroyed());
        for (Chest chest : player.getChests()) {
            System.out.println(chest);
        }
    }

    @Test
    public void PlayerShouldStartWithTheCorrectAmountOfCardsInHand() {
        assertEquals(3, player.getNbrCardsInHand());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 15, 31})
    public void TheNumberOfActionPointMustBeCorrect(int turn) {
        player.playTurn(turn);
        assertEquals((Math.min(turn, 15)), player.getActionPoints());
    }

    @Test
    public void PlayerShouldHaveTheCorrectAmountOfEggs() {
        for (Chest chest : player.getChests()) {
            System.out.println(chest);
        }
        assertEquals(Player.getStartingNbrChests(), player.getNbChests());
    }
}
