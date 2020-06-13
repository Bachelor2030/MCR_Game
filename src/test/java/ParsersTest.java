import Client.View.GameBoard;
import Server.Game.Game;
import Common.Network.JsonUtils.ParserLauncher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class ParsersTest {
    private static Game game;

    @BeforeAll
    public static void init() {
        String file = "src/main/resources/json/game.json";
        GameBoard gameBoard = new GameBoard();

        game = ParserLauncher.parseJsonGame(file, gameBoard.getBoard());
    }

    @Test
    public void theParserLauncherShouldCreateANonNullGame() {
        assertNotNull(game);
    }

    @Test
    public void theParserLauncherShouldCreateAGameWithTwoPlayers() {
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
    }

    @Test
    public void TheNewlyCreatedGameShouldHaveEmptyDiscardPiles() {
        assertTrue(game.getPlayer1().getDiscard().isEmpty());
        assertTrue(game.getPlayer2().getDiscard().isEmpty());
    }

    @Test
    public void TheNewlyCreatedGameShouldBeAtTurnZero() {
        assertEquals(0, game.getTurn());
    }
}
