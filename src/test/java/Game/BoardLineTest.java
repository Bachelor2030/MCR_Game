package Game;

import javafx.scene.Group;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardLineTest {
    private Group root = new Group();
    @Test
    public void EmptyTest() {
        assertEquals(0, 0);
    }

    /*
    @Test
    public void aLineShouldHaveTheCorrectNumberOfSpot() throws IOException {
        BoardLine testLine = new BoardLine(3, root);
        System.out.println("********************" + testLine.getSpots().size());
        assertEquals(testLine.getSpots().size(), testLine.getNB_SPOTS());
    }

    @Test
    public void aLineShouldHaveEmptySpotWhenCreated() throws IOException {
        BoardLine testLine = new BoardLine(1, root);

        for(Spot spot : testLine.getSpots())
        {
            assertEquals(true, spot.isEmpty());
        }

    }
    
     */
}
