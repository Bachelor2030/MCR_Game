package Game;

import Common.GameBoard.Line;
import Common.GameBoard.Spot;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineTest {
    @Test
    public void EmptyTest() {
        assertEquals(0, 0);
    }

    @Test
    public void aLineShouldHaveTheCorrectNumberOfSpot() throws IOException {
        Line testLine = new Line(3);
        assertEquals(testLine.getSpots().size(), testLine.getNB_SPOTS());
    }

    @Test
    public void aLineShouldHaveEmptySpotWhenCreated() throws IOException {
        Line testLine = new Line(1);

        for(Spot spot : testLine.getSpots())
        {
            assertEquals(true, spot.isEmpty());
        }

    }
}
