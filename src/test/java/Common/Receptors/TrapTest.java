package Common.Receptors;

import Server.Game.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrapTest {
    private static String name = "Trap";
    private static Trap trap;

    @BeforeAll
    public static void init() {
        trap = new Trap(name, null);
    }

    @Test
    public void NameShouldBeCorrect() {
        assertEquals(name, trap.getName());
    }

    @Test
    public void PositionShouldBeCorrect() {
        Position p = new Position();
        assertNull(trap.getPosition());
        trap.setPosition(p);
        assertEquals(p, trap.getPosition());
    }

    @Test
    public void IfTriggeredTheTrapMustDisapear() {
        Position p = new Position();
        trap.setPosition(p);
        assertEquals(p, trap.getPosition());
        trap.trigger(null);
        assertNull(trap.getPosition());
    }
}