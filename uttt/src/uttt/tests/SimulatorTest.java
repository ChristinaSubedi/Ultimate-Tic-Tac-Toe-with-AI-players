package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uttt.UTTTFactory;

import uttt.game.BoardInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

public class SimulatorTest {

    private BoardInterface board1;
    private BoardInterface board2;
    private BoardInterface board3;
    private BoardInterface board4;
    private BoardInterface board5;
    private BoardInterface board6;
    private BoardInterface board7;
    private BoardInterface board8;
    private BoardInterface board0;

    private SimulatorInterface simulator;

    @Before
    public void initializeBoad() {
        board0 = UTTTFactory.createBoard();
        board1 = UTTTFactory.createBoard();
        board2 = UTTTFactory.createBoard();
        board3 = UTTTFactory.createBoard();
        board4 = UTTTFactory.createBoard();
        board5 = UTTTFactory.createBoard();
        board6 = UTTTFactory.createBoard();
        board7 = UTTTFactory.createBoard();
        board8 = UTTTFactory.createBoard();

        simulator = UTTTFactory.createSimulator();

        assertNotNull("board inititalized to null", board1);

    }

    @Test
    public void general() {
        simulator.setBoards(new BoardInterface[] { board0, board1, board2, board3,
                board4, board5, board6, board7, board8 });

        BoardInterface[] board = simulator.getBoards();

        assertEquals(9, board.length);
        assertNotNull(board);

        simulator.setCurrentPlayerSymbol(Symbol.CROSS);

        assertTrue(simulator.setMarkAt(Symbol.CROSS, 2, 3));
        assertTrue(simulator.setMarkAt(Symbol.CROSS, 3, 3));
        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        assertFalse(simulator.setMarkAt(Symbol.CIRCLE, 5, 3));

        simulator.setIndexNextBoard(4);
        assertTrue(simulator.setMarkAt(Symbol.CIRCLE, 4, 4));
        assertFalse(simulator.setMarkAt(Symbol.CIRCLE, 4, 4));
        assertFalse(simulator.setMarkAt(Symbol.CIRCLE, 5, 4));

        board = simulator.getBoards();
        assertEquals("expected cross", Symbol.CROSS, board[3].getMarks()[3].getSymbol());
        assertEquals("expected index 2", 2, board[2].getMarks()[2].getPosition());

        assertEquals("expected cross", Symbol.CROSS, board[3].getMarks()[3].getSymbol());
        assertEquals("expected index 3", 3, board[3].getMarks()[3].getPosition());

        assertEquals("expected circle", Symbol.CIRCLE, board[4].getMarks()[4].getSymbol());
        assertEquals("expected index 4", 4, board[4].getMarks()[4].getPosition());

        simulator.setCurrentPlayerSymbol(Symbol.CROSS);
        assertEquals("expected Cross", simulator.getCurrentPlayerSymbol(), Symbol.CROSS);

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        assertEquals("expected Circle", simulator.getCurrentPlayerSymbol(), Symbol.CIRCLE);

        simulator.setIndexNextBoard(5);
        assertEquals(5, simulator.getIndexNextBoard());

        simulator.setIndexNextBoard(-1);
        assertEquals(-1, simulator.getIndexNextBoard());

        assertFalse("game not over", simulator.isGameOver());

        assertTrue("move should be possible", simulator.isMovePossible(5));
        assertTrue("move should be possible", simulator.isMovePossible(8));

        simulator.setIndexNextBoard(5);
        assertTrue("move should be possible", simulator.isMovePossible(5));
        assertFalse("move should not be possible", simulator.isMovePossible(7));

        simulator.setMarkAt(Symbol.CIRCLE, 5, 4);

        assertTrue("move should be possible", simulator.isMovePossible(4, 0));
        assertFalse("move should not be possible", simulator.isMovePossible(5, 4));

        assertEquals(Symbol.EMPTY, simulator.getWinner());

    }

    @Test
    public void setMarkAt() {
        simulator.setBoards(new BoardInterface[] { board0, board1, board2, board3,
                board4, board5, board6, board7, board8 });

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);

        assertTrue(simulator.setMarkAt(Symbol.CIRCLE, 0, 0));
        assertTrue(simulator.setMarkAt(Symbol.CIRCLE, 0, 1));

        assertFalse(simulator.setMarkAt(Symbol.CIRCLE, 0, 1));
        simulator.setCurrentPlayerSymbol(Symbol.CROSS);

        assertTrue(simulator.setMarkAt(Symbol.CROSS, 1, 0));

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        assertTrue(simulator.setMarkAt(Symbol.CIRCLE, 0, 2));
        simulator.setIndexNextBoard(0);
        assertFalse(simulator.setMarkAt(Symbol.CIRCLE, 0, 4));
    }

    // illegal tests
    @Test(expected = IllegalArgumentException.class)
    public void setBoardNull() {
        simulator.setBoards(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCurrSymbolNull() {
        simulator.setCurrentPlayerSymbol(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setBoardIncomplete() {
        simulator.setBoards(new BoardInterface[] { board0, board1, board2, board3,
                board4, board5, board6, board7 });
    }

    // illegal tests
    @Test(expected = IllegalArgumentException.class)
    public void setSymbolNull() {
        simulator.setCurrentPlayerSymbol(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongCurrPlayer() {
        simulator.setMarkAt(Symbol.CIRCLE, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongCurrPlayer2() {
        simulator.setCurrentPlayerSymbol(Symbol.CROSS);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMarkAtNull() {
        simulator.setMarkAt(Symbol.CROSS, 0, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMarkAtNull1() {
        simulator.setMarkAt(Symbol.CROSS, 9, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMarkAtNull2() {
        simulator.setMarkAt(Symbol.CROSS, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMarkAtNull4() {
        simulator.setMarkAt(null, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIndexNeg() {
        simulator.setIndexNextBoard(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIndexEdge() {
        simulator.setIndexNextBoard(9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void movePosNeg() {
        simulator.isMovePossible(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void smovePosEdge() {
        simulator.isMovePossible(9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void movePos2Edge() {
        simulator.isMovePossible(0, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void movePos2Neg() {
        simulator.isMovePossible(0, -1);
    }

}
