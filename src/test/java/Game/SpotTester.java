package Game;

import Game.GameBoard.Spot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpotTester {
    @Test
    public void EmptyTest() {
        assertEquals(0, 0);
    }

    @Test
    public void aSpotShouldBeEmptyWhenCreated()
    {
        Spot testSpot = new Spot();
        assertEquals(testSpot.isBusy(), false);
    }
}
