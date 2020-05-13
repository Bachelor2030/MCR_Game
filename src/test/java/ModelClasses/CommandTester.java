package ModelClasses;

import Game.GameBoard.Position;
import ModelClasses.Commands.CreateCreature;
import Game.GameBoard.Spot;
import ModelClasses.Receptors.Creature.Creature;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandTester {

    @Test
    public void CreatingCreatureShouldPlaceIt() throws IOException {
        Creature pier = new Creature("Pier", 12, 12, 4,null);
        Position position = new Position();

        CreateCreature createCreature = new CreateCreature(pier, position);
        assertNull(pier.getPosition());

        createCreature.execute();
        assertEquals(position, pier.getPosition());
    }
}
