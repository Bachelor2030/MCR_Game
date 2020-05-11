package ModelClasses;

import ModelClasses.Commands.CreateCreature;
import Game.GameBoard.Spot;
import ModelClasses.Receptors.Creature.Creature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandTester {

    @Test
    public void CreatingCreatureShouldPlaceIt() {
        Creature pier = new Creature("Pier", 12, 12);
        Spot position = new Spot();

        CreateCreature createCreature = new CreateCreature(pier, position);
        assertNull(pier.getPosition());

        createCreature.execute();
        assertEquals(position, pier.getPosition());
    }
}
