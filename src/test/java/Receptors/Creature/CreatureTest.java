package Receptors.Creature;

import Common.Receptors.Creature;
import Common.Receptors.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatureTest {
    private static final int STEPS = 2;
    private static Creature creature;
    private static Creature creature1;
    private static Player player;
    private static Player player1;

    @BeforeAll
    public static void init() throws FileNotFoundException {
        player    = new Player("Elodie", null);
        player1   = new Player("Michel", null);

        creature  = new Creature("Pier", 10, STEPS, 4);
        creature.setOwner(player);
        creature1 = new Creature("Sebas-chan", 12, 3, 1);
        creature1.setOwner(player);
    }

    @Test
    public void CreaturesWithSameOwnerMustBeAllies() {
        assertTrue(creature.isAlly(creature1));
    }

    @Test
    public void CreaturesWithDifferentOwnersMustNotBeAllies() throws FileNotFoundException {
        Creature c = new Creature("", 0, 0, 4);
        c.setOwner(player1);
        assertFalse(creature.isAlly(c));
    }

    @Test
    public void CreatureMustHaveCorrectNumberOfSteps() {
        assertEquals(STEPS, creature.getSteps());
    }

    @Test
    public void CreatureMustMoveWhenItsTurnIsPlayed() {
        /*
        Position p = new Position(new BoardLine(1), 1);
        Position f = new Position(p.getBoardLine(), p.getPosition() + creature.getSteps());

        creature.place(p);
        creature.advance();

        assertEquals(f.getBoardLine(), creature.getPosition().getBoardLine());
        assertEquals(f.getPosition(), creature.getPosition().getPosition());

        creature.retreat(creature.getSteps());

        assertEquals(p.getBoardLine(), creature.getPosition().getBoardLine());
        assertEquals(p.getPosition(), creature.getPosition().getPosition());
        */
    }

    @Test
    public void AdvancingToAnEnemyMustTriggerAHit() {
        /*
        Creature c = new Creature("", 20, 0, 4, player1);
        Position p = new Position(new BoardLine(1), 1);
        Position p1 = p.next();
        creature.place(p);
        c.place(p1);

        creature.playTurn(10);

        assertEquals(c.getMAX_LIFE_POINTS() - creature.getAttackPoints(), c.getLifePoints());
        assertEquals(creature.getMAX_LIFE_POINTS() - c.getAttackPoints(), creature.getLifePoints());
        */
    }
}
