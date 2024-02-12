package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.MarkInterface;
import uttt.game.BoardInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

public class SimpleTest {

	SimulatorInterface simulator;
	private BoardInterface board;

	@Before
	public void setUp() throws Exception {
		// simulator = UTTTFactory.createSimulator();
		board = UTTTFactory.createBoard();

	}

	@Test
	public void simpleSetPieceTest() {
		// assertNotNull(simulator);
	}

	// general check of all functionalities
	@Test
	public void BoardTest1() {
		BoardInterface board1 = UTTTFactory.createBoard();
		assertNotNull("BoardCreation returned null", board1);

		MarkInterface[] markArray = board1.getMarks();
		assertNotNull("GetMarks returned null", markArray);

		for (int i = 0; i < 9; i++) {
			assertTrue("Marks not initialized to empty", Symbol.EMPTY == markArray[i].getSymbol());
			assertTrue("Mark index is incorrect" + markArray[i].getPosition(),
					i == markArray[i].getPosition());
		}

		board1.setMarkAt(Symbol.CIRCLE, 0);
		board1.setMarkAt(Symbol.CIRCLE, 4);

		assertFalse("game not closed but got close", board1.isClosed());
		assertEquals("expected winner empty", Symbol.EMPTY, board1.getWinner());

		board1.setMarkAt(Symbol.CIRCLE, 8);

		assertTrue("game closed but got not close", board1.isClosed());

		board1.setMarkAt(Symbol.CROSS, 6);

		markArray = board1.getMarks();

		assertTrue("Expected circle", Symbol.CIRCLE == markArray[0].getSymbol());

		assertFalse("move should not have been possible", board1.isMovePossible(0));
		assertFalse("move should not have been possible", board1.isMovePossible(4));
		assertFalse("move should not have been possible", board1.isMovePossible(6));
		assertFalse("move should not have been possible", board1.isMovePossible(8));

		assertFalse("move should not have been possible", board1.isMovePossible(1));

		assertEquals("expected winner circle", Symbol.CIRCLE, board1.getWinner());

	}

	// test for setMarks
	@Test
	public void setMarkTest() {
		BoardInterface board1 = UTTTFactory.createBoard();
		MarkInterface[] marks = new MarkInterface[9];

		Symbol symbol;
		for (int i = 0; i < 9; i++) {
			symbol = (i % 2 == 0) ? Symbol.CROSS : Symbol.CIRCLE;
			marks[i] = UTTTFactory.createMark(symbol, i);
		}

		board1.setMarks(marks);
		MarkInterface[] boardMark = board1.getMarks();

		assertEquals(9, boardMark.length);

		for (int i = 0; i < 9; i++) {
			symbol = (i % 2 == 0) ? Symbol.CROSS : Symbol.CIRCLE;
			assertEquals(symbol, boardMark[i].getSymbol());

		}
	}

	// test all illegal cases
	@Test(expected = IllegalArgumentException.class)
	public void negIndex() {
		board.setMarkAt(Symbol.CROSS, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void outIndex() {
		board.setMarkAt(Symbol.CROSS, 9);

	}

	@Test(expected = IllegalArgumentException.class)
	public void markNull() {
		board.setMarkAt(null, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void possibleNeg() {
		board.isMovePossible(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void possibleBounds() {
		board.isMovePossible(50);

	}

	@Test(expected = IllegalArgumentException.class)
	public void markArrayEmpty() {
		Symbol symbol;
		MarkInterface[] marks = new MarkInterface[9];
		for (int i = 0; i < 9; i++) {
			symbol = (i % 2 == 0) ? Symbol.CROSS : Symbol.CIRCLE;
			if (i == 8) {
				symbol = null;
			}
			marks[i] = UTTTFactory.createMark(symbol, i);
		}

		board.setMarks(marks);
	}

	@Test(expected = IllegalArgumentException.class)
	public void symbolNeg() {
		Symbol symbol;
		MarkInterface[] marks = new MarkInterface[9];
		for (int i = 0; i < 9; i++) {
			symbol = (i % 2 == 0) ? Symbol.CROSS : Symbol.CIRCLE;

			marks[i] = UTTTFactory.createMark(symbol, i * 3);
		}

		board.setMarks(marks);
	}

	@Test(expected = IllegalArgumentException.class)
	public void symbolOutBound() {
		Symbol symbol;
		MarkInterface[] marks = new MarkInterface[9];
		for (int i = 0; i < 9; i++) {
			symbol = (i % 2 == 0) ? Symbol.CROSS : Symbol.CIRCLE;
			marks[i] = UTTTFactory.createMark(symbol, -i);
		}

		board.setMarks(marks);
	}

	// set MarkAt test
	@Test
	public void setMarkAtTest() {
		board.setMarkAt(Symbol.CROSS, 0);
		board.setMarkAt(Symbol.CIRCLE, 1);
		board.setMarkAt(Symbol.CROSS, 2);
		board.setMarkAt(Symbol.CIRCLE, 5);
		board.setMarkAt(Symbol.CROSS, 7);

		MarkInterface[] mark = board.getMarks();

		assertEquals("wrong mark", Symbol.CROSS, mark[0].getSymbol());
		assertEquals("wrong mark", Symbol.CIRCLE, mark[1].getSymbol());
		assertEquals("wrong mark", Symbol.CROSS, mark[2].getSymbol());
		assertEquals("wrong mark", Symbol.CIRCLE, mark[5].getSymbol());
		assertEquals("wrong mark", Symbol.CROSS, mark[7].getSymbol());

		assertEquals("wrong mark", Symbol.EMPTY, mark[8].getSymbol());

		assertEquals("wrong mark", 0, mark[0].getPosition());
		assertEquals("wrong mark", 1, mark[1].getPosition());
		assertEquals("wrong mark", 2, mark[2].getPosition());

	}

	// Everything else, mixed with winner
	@Test
	public void Win1() {
		board.setMarkAt(Symbol.CROSS, 0);
		board.setMarkAt(Symbol.CROSS, 1);

		assertFalse(board.isMovePossible(1));
		assertTrue(board.isMovePossible(5));
		assertFalse(board.isClosed());
		assertEquals(Symbol.EMPTY, board.getWinner());

		board.setMarkAt(Symbol.CROSS, 2);
		board.setMarkAt(Symbol.CIRCLE, 3);
		board.setMarkAt(Symbol.CIRCLE, 7);

		assertTrue(board.isClosed());

		assertEquals(Symbol.CROSS, board.getWinner());

	}

	@Test
	public void Win2() {
		board.setMarkAt(Symbol.CROSS, 0);
		board.setMarkAt(Symbol.CROSS, 3);

		assertFalse(board.isClosed());
		assertEquals(Symbol.EMPTY, board.getWinner());

		board.setMarkAt(Symbol.CROSS, 2);
		board.setMarkAt(Symbol.CIRCLE, 1);
		board.setMarkAt(Symbol.CIRCLE, 4);
		board.setMarkAt(Symbol.CIRCLE, 7);

		assertTrue(board.isClosed());
		assertEquals(Symbol.CIRCLE, board.getWinner());

	}

	@Test
	public void Win3Diag() {
		board.setMarkAt(Symbol.CROSS, 0);
		board.setMarkAt(Symbol.CROSS, 4);

		assertFalse(board.isClosed());
		assertEquals(Symbol.EMPTY, board.getWinner());

		board.setMarkAt(Symbol.CROSS, 2);
		board.setMarkAt(Symbol.CIRCLE, 1);
		board.setMarkAt(Symbol.CROSS, 8);
		board.setMarkAt(Symbol.CIRCLE, 7);

		assertTrue(board.isClosed());
		assertEquals(Symbol.CROSS, board.getWinner());

	}

	@Test
	public void Win3DiagInv() {
		board.setMarkAt(Symbol.CROSS, 2);
		board.setMarkAt(Symbol.CROSS, 4);

		assertFalse(board.isClosed());
		assertEquals(Symbol.EMPTY, board.getWinner());

		board.setMarkAt(Symbol.CIRCLE, 1);
		board.setMarkAt(Symbol.CROSS, 6);
		board.setMarkAt(Symbol.CIRCLE, 7);

		assertTrue(board.isClosed());
		assertEquals(Symbol.CROSS, board.getWinner());

	}

	@Test
	public void fullBoard() {
		board.setMarkAt(Symbol.CROSS, 0);
		board.setMarkAt(Symbol.CROSS, 1);

		assertFalse(board.isClosed());
		assertEquals(Symbol.EMPTY, board.getWinner());

		board.setMarkAt(Symbol.CIRCLE, 2);

		board.setMarkAt(Symbol.CIRCLE, 3);
		board.setMarkAt(Symbol.CIRCLE, 4);
		board.setMarkAt(Symbol.CROSS, 5);

		board.setMarkAt(Symbol.CROSS, 6);
		board.setMarkAt(Symbol.CIRCLE, 7);
		board.setMarkAt(Symbol.CIRCLE, 8);

		assertTrue(board.isClosed());
		assertEquals(Symbol.EMPTY, board.getWinner());

	}

}
