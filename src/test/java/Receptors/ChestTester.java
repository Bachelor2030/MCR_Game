package Receptors;

import Common.Receptors.Chest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ChestTester {
    private static Chest chest;
    private static final String name = "Saphira";

    @BeforeAll
    private static void init() {
        chest = new Chest(name);
    }

    @Test
    public void ChestsNameShouldBeCorrect() {
        assertEquals(name, chest.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 5, 13})
    public void HittingAChestShouldDiminishItsLife(int points) {
        chest.hit(points);
        if (points < chest.getMAX_LIFE_POINTS()) {
            assertEquals(chest.getMAX_LIFE_POINTS() - points, chest.getLifePoints());
            assertTrue(chest.isAlive());
        } else {
            assertEquals(0, chest.getLifePoints());
            assertFalse(chest.isAlive());
        }
    }
}
