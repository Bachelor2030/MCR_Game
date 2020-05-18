package Receptors.Creature;

import Common.Receptors.Creature;
import Server.Game.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatureTester {
    private static final int STEPS = 2;
    private static Creature creature;
    private static Creature creature1;

    @BeforeAll
    public static void init() {
        creature = new Creature("Pier", 10, STEPS, 4, "Elodie");
        creature1 = new Creature("Sebas-chan", 12, 3, 1, "Elodie");
    }

    @Test
    public void CreaturesWithSameOwnerMustBeAllies() {
        assertTrue(creature.isAlly(creature1));
    }

    @Test
    public void CreaturesWithDifferentOwnersMustNotBeAllies() {
        Creature c = new Creature("", 0, 0, 4, "Me");
        assertFalse(creature.isAlly(c));
    }

    @Test
    public void CreatureMustHaveCorrectNumbreOfSteps() {
        assertEquals(STEPS, creature.getSteps());
    }

    @Test
    public void CreatureMustMoveWhenItsTurnIsPlayed() {
        Position p = new Position();
        Position f = new Position(p.getLine(), p.getPosition() + creature.getSteps());

        creature.place(p);
        creature.advance();

        assertEquals(f.getLine(), creature.getPosition().getLine());
        assertEquals(f.getPosition(), creature.getPosition().getPosition());

        creature.retreat(creature.getSteps());

        assertEquals(p.getLine(), creature.getPosition().getLine());
        assertEquals(p.getPosition(), creature.getPosition().getPosition());
    }

    @Test
    public void AdvancingToAnEnemyMustTriggerAHit() {
        Creature c = new Creature("", 20, 0, 4, "Me");
        Position p = new Position();
        Position p1 = p.next();
        creature.place(p);
        c.place(p1);

        creature.playTurn(10);

        assertEquals(c.getMAX_LIFE_POINTS() - creature.getAttackPoints(), c.getLifePoints());
        assertEquals(creature.getMAX_LIFE_POINTS() - c.getAttackPoints(), creature.getLifePoints());
    }
}
