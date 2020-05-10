package GameBoard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTester {

    @Test
    public void EmptyTest() {
        assertEquals(0, 0);
    }

    @Test
    public void aBoardShouldHaveTheCorrectNumberOfLines() {
        Board testBoard = new Board();
        assertEquals(testBoard.getLines().size(), testBoard.getNB_LINES());
    }

    @Test
    public void aLineContainedInBoardShouldHaveACorrectId()
    {
        Board testBoard = new Board();
        for(int indexCheck = 0; indexCheck < testBoard.getNB_LINES(); ++indexCheck)
        {
            assertEquals(testBoard.getLines().get(indexCheck).getNoLine(), indexCheck + 1);
        }
    }
}
