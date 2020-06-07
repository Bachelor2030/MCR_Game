package Game;

import Common.GameBoard.Spot;
import javafx.scene.Group;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpotTester {
    private Group root = new Group();
    @Test
    public void EmptyTest() {
        assertEquals(0, 0);
    }

    /*
    @Test
    public void aSpotShouldBeEmptyWhenCreated() throws IOException {
        Spot testSpot = new Spot(root);
        assertEquals(true, testSpot.isEmpty());
    }

     */
}
