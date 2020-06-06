package ModelClasses;

import Server.Game.Position;
import Server.Game.Card.Commands.CreateCreature;
import Common.Receptors.Creature;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandTester {

    @Test
    public void CreatingCreatureShouldPlaceIt() throws IOException {
        Creature pier = new Creature("Pier", 12, 12, 4,null);
        Position position = new Position();

        CreateCreature createCreature = new CreateCreature();
        createCreature.setCreature(pier);
        createCreature.setPosition(position);
        assertNull(pier.getPosition());

        createCreature.execute();
        assertEquals(position, pier.getPosition());
    }
}
