package Receptors;

import Common.GameBoard.Line;
import Common.Receptors.Creature;
import Common.Receptors.Trap;
import Server.Game.ModelClasses.ConcreteCommand;
import Server.Game.ModelClasses.Commands.OnLiveReceptors.Hit;
import Server.Game.ModelClasses.Macro;
import Server.Game.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TrapTest {
    private static Trap trap;
    private static Creature creature1, creature2, creature3;
    private static int lp1, lp2, lp3;
    private static int attackPoints = 12;

    @BeforeAll
    public static void init() {
        ArrayList<ConcreteCommand> concreteCommands = new ArrayList<>();

        creature1 = new Creature("Test creature", lp1, 23, 3);
        creature2 = new Creature("Test creature", lp2, 23, 3);
        creature3 = new Creature("Test creature", lp3, 23, 3);

        Hit h = new Hit();
        h.setAttackPoints(attackPoints);
        //h.setReceptors(new LiveReceptor[]{creature1, creature2, creature3});

        concreteCommands.add(new Hit());

        trap = new Trap("Test trap", new Macro(concreteCommands));

    }

    @Test
    public void placingTheTrapShouldGiveItTheGivenPosition() {
        Position pos = new Position(new Line(1), 1);
        trap.setPosition(pos);
        assertEquals(pos, trap.getPosition());
    }

    @Test
    public void triggeringTheTrapShouldExecuteItsCommandsOnTheGivenVictimAndRemoveItFromThePosition() {
        trap.trigger(creature1);

        assertEquals((Math.max(0, lp1 - attackPoints)), creature1.getLifePoints());
        assertEquals(lp2, creature2.getLifePoints());
        assertEquals(lp3, creature3.getLifePoints());
        assertNull(trap.getPosition());
    }

    @Test
    public void playingTrapTurnShouldDoNothing() {
        trap.playTurn(12);
    }
}
