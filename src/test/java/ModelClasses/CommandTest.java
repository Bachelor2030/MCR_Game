package ModelClasses;

import Common.GameBoard.Board;
import Server.Game.Position;
import Server.Game.Card.Commands.CreateCreature;
import Common.Receptors.Creature;
import javafx.scene.Group;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandTest {
    private Group root = new Group();
/*
    @Test
    public void CreatingCreatureShouldPlaceIt() throws IOException {
        Group root = new Group();
        Board board = new Board(root);
        Creature pier = new Creature("Pier", 12, 12, 4,null);
        Position position = new Position(board.getLines().get(0),0);

        CreateCreature createCreature = new CreateCreature(pier, position);
        assertNull(pier.getPosition());

        createCreature.execute();
        assertEquals(position, pier.getPosition());
    }
    
 */
}
