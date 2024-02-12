package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uttt.UTTTFactory;

import uttt.game.BoardInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

public class SimulatorIsClosed {

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
    public void fullBoard() {
        BoardInterface[] boards = new BoardInterface[] { board0, board1, board2, board3,
                board4, board5, board6, board7, board8 };
        for (int i = 0; i < 8; i++) {
            boards[i].setMarkAt(Symbol.CIRCLE, 0);
            boards[i].setMarkAt(Symbol.CIRCLE, 1);
            boards[i].setMarkAt(Symbol.CIRCLE, 4);
            boards[i].setMarkAt(Symbol.CIRCLE, 5);
            boards[i].setMarkAt(Symbol.CIRCLE, 6);
            boards[i].setMarkAt(Symbol.CROSS, 2);
            boards[i].setMarkAt(Symbol.CROSS, 3);
            boards[i].setMarkAt(Symbol.CROSS, 7);
            boards[i].setMarkAt(Symbol.CROSS, 8);

        }

        simulator.setBoards(boards);
        assertFalse("board is closed", simulator.isMovePossible(1));
        assertFalse("board is closed", simulator.isMovePossible(1, 1));
        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        assertFalse(simulator.setMarkAt(Symbol.CIRCLE, 3, 3));
        assertTrue(simulator.setMarkAt(Symbol.CIRCLE, 8, 8));
        assertTrue("board not closed", simulator.isMovePossible(8));
        assertTrue("board not closed", simulator.isMovePossible(8, 1));
        assertTrue(simulator.setMarkAt(Symbol.CIRCLE, 8, 1));

        int i = 8;
        boards[i].setMarkAt(Symbol.CIRCLE, 0);
        boards[i].setMarkAt(Symbol.CIRCLE, 1);
        boards[i].setMarkAt(Symbol.CIRCLE, 4);
        boards[i].setMarkAt(Symbol.CIRCLE, 5);
        boards[i].setMarkAt(Symbol.CIRCLE, 6);
        boards[i].setMarkAt(Symbol.CROSS, 2);
        boards[i].setMarkAt(Symbol.CROSS, 3);
        boards[i].setMarkAt(Symbol.CROSS, 7);
        boards[i].setMarkAt(Symbol.CROSS, 8);
        simulator.setBoards(boards);

        assertTrue("board is full", simulator.isGameOver());

        assertFalse("board is full", simulator.isMovePossible(1));
        assertFalse("board is full", simulator.isMovePossible(3, 3));
        assertEquals("no winner", Symbol.EMPTY, simulator.getWinner());

    }

    @Test
    public void wonBoard() {
        BoardInterface[] boards = new BoardInterface[] { board0, board1, board2, board3,
                board4, board5, board6, board7, board8 };

        for (int i = 0; i < 3; i++) {
            Symbol curreSymbol = Symbol.CROSS;
            for (int j = 0; j < 9; j++) {
                boards[i].setMarkAt(curreSymbol, j);
                curreSymbol = curreSymbol == Symbol.CIRCLE ? Symbol.CROSS : Symbol.CIRCLE;
            }
            simulator.setBoards(boards);
            assertFalse("board is full", simulator.isMovePossible(0));
            assertFalse("board is full", simulator.isMovePossible(0, 3));
        }

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        assertFalse(simulator.setMarkAt(Symbol.CIRCLE, 3, 0));

        assertFalse("game has been won", simulator.isMovePossible(4));
        assertFalse("board is full", simulator.isMovePossible(3, 3));
        assertEquals("cross should win", Symbol.CROSS, simulator.getWinner());

    }

}
