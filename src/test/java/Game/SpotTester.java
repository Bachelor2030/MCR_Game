package Game;

import Game.GameBoard.Spot;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpotTester {
    @Test
    public void EmptyTest() {
        assertEquals(0, 0);
    }

    @Test
    public void aSpotShouldBeEmptyWhenCreated() throws IOException {
        Spot testSpot = new Spot();
        assertEquals(testSpot.isBusy(), false);
    }
}
