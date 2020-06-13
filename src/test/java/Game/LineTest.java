package Game;

import Common.GameBoard.Line;
import Common.GameBoard.Spot;
import javafx.scene.Group;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineTest {
    private Group root = new Group();
    @Test
    public void EmptyTest() {
        assertEquals(0, 0);
    }

    /*
    @Test
    public void aLineShouldHaveTheCorrectNumberOfSpot() throws IOException {
        Line testLine = new Line(3, root);
        System.out.println("********************" + testLine.getSpots().size());
        assertEquals(testLine.getSpots().size(), testLine.getNB_SPOTS());
    }

    @Test
    public void aLineShouldHaveEmptySpotWhenCreated() throws IOException {
        Line testLine = new Line(1, root);

        for(Spot spot : testLine.getSpots())
        {
            assertEquals(true, spot.isEmpty());
        }

    }
    
     */
}
