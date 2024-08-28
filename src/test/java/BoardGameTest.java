
import boardgame.BoardGame;
import boardgame.Position;
import boardgame.Square;
import game.State;
import game.State.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BoardGameTest {

    private BoardGame boardGame;

    @BeforeEach
    public void setUp() {
        boardGame = new BoardGame();
    }

    @Test
    public void testInitialSetup() {
        assertNotNull(boardGame);
        assertEquals(State.Player.PLAYER_1, boardGame.getNextPlayer());
    }

    @Test
    public void testSquareProperty() {
        Position pos = new Position(0, 0);
        assertNotNull(boardGame.squareProperty(pos.row(), pos.col()));
    }

    @Test
    public void testGetSquare() {
        Position pos = new Position(0, 0);
        assertNotNull(boardGame.getSquare(pos));
    }

    @Test
    public void testCanMove() {
        Position from = new Position(0, 0);
        Position to = new Position(1, 0);
        if (boardGame.isLegalToMoveFrom(from)) {
            assertTrue(boardGame.canMove(from, to));
        }
    }

    @Test
    public void testIsLegalToMoveFrom() {
        Position pos = new Position(0, 0);
        if (boardGame.getSquare(pos) == Square.WHITE_QUEEN) {
            assertTrue(boardGame.isLegalToMoveFrom(pos));
        } else {
            assertFalse(boardGame.isLegalToMoveFrom(pos));
        }
    }

    @Test
    public void testIsLegalMove() {
        Position from = new Position(0, 0);
        Position to = new Position(1, 0);
        assertTrue(boardGame.isLegalMove(from, to));
    }

    @Test
    public void testMakeMove() {
        Position from = new Position(0, 0);
        Position to = new Position(1, 0);
        if (boardGame.isLegalToMoveFrom(from) && boardGame.isLegalMove(from, to)) {
            boardGame.makeMove(from, to);
            assertEquals(Square.NONE, boardGame.getSquare(from));
            assertEquals(Square.WHITE_QUEEN, boardGame.getSquare(to));
        }
    }



    @Test
    public void testIsGameOver() {
        Position pos = new Position(BoardGame.BOARD_ROW - 1, 0);
        if (boardGame.getSquare(pos) == Square.WHITE_QUEEN) {
            assertTrue(boardGame.isGameOver());
        } else {
            assertFalse(boardGame.isGameOver());
        }
    }

    @Test
    public void testGetStatus() {
        assertEquals(Status.IN_PROGRESS, boardGame.getStatus());
        Position pos = new Position(BoardGame.BOARD_ROW - 1, 0);
        if (boardGame.getSquare(pos) == Square.WHITE_QUEEN) {
            assertEquals(Status.PLAYER_1_WINS, boardGame.getStatus());
        }
    }

    @Test
    public void testIsEmpty() {
        Position pos = new Position(0, 0);
        assertTrue(boardGame.isEmpty(pos));
    }

    @Test
    public void testSetAndGetPlayerNames() {
        boardGame.setPlayer1Name("Alice");
        boardGame.setPlayer2Name("Bob");
        assertEquals("Alice", boardGame.getPlayer1Name());
        assertEquals("Bob", boardGame.getPlayer2Name());
    }

}
