package GameBoard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineTester {
    @Test
    public void EmptyTest() {
        assertEquals(0, 0);
    }

    @Test
    public void aLineShouldHaveTheCorrectNumberOfSpot()
    {
        Line testLine = new Line(3);
        assertEquals(testLine.getSpots().size(), testLine.getNB_SPOTS());
    }

    @Test
    public void aLineShouldHaveEmptySpotWhenCreated() {
        Line testLine = new Line(1);

        for(Spot spot : testLine.getSpots())
        {
            assertEquals(spot.isBusy(), false);
        }

    }
}
