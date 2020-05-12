package Receptors;

import ModelClasses.Receptors.Egg;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class EggTester {
    private static Egg egg;
    private static final String name = "Saphira";

    @BeforeAll
    private static void init() {
        egg = new Egg(name);
    }

    @Test
    public void EggsNameShouldBeCorrect() {
        assertEquals(name, egg.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 5, 13})
    public void HittingAnEggShouldDiminishItsLife(int points) {
        egg.hit(points);
        if (points < egg.getMAX_LIFE_POINTS()) {
            assertEquals(egg.getMAX_LIFE_POINTS() - points, egg.getLifePoints());
            assertTrue(egg.isAlive());
        } else {
            assertEquals(0, egg.getLifePoints());
            assertFalse(egg.isAlive());
        }
    }
}
