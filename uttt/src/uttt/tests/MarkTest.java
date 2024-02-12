package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class MarkTest {

    @Test
    public void createMark() {
        MarkInterface cross1 = UTTTFactory.createMark(Symbol.CROSS, 0);
        assertNotNull("mark is null", cross1);
        assertEquals("wrong position", 0, cross1.getPosition());
        assertEquals("wrong value", Symbol.CROSS, cross1.getSymbol());

        MarkInterface circle1 = UTTTFactory.createMark(Symbol.CIRCLE, 1);
        assertNotNull("mark is null", circle1);
        assertEquals("wrong position", 1, circle1.getPosition());
        assertEquals("wrong value", Symbol.CIRCLE, circle1.getSymbol());

        MarkInterface circle2 = UTTTFactory.createMark(Symbol.CIRCLE, 8);
        assertNotNull("mark is null", circle2);
        assertEquals("wrong position", 8, circle2.getPosition());
        assertEquals("wrong value", Symbol.CIRCLE, circle2.getSymbol());

        MarkInterface empty = UTTTFactory.createMark(Symbol.EMPTY, 5);
        assertNotNull("mark is null", empty);
        assertEquals("wrong position", 5, empty.getPosition());
        assertEquals("wrong value", Symbol.EMPTY, empty.getSymbol());

        empty.setSymbol(Symbol.CROSS);
        ;
        assertNotNull("mark is null", empty);
        assertEquals("wrong position", 5, empty.getPosition());
        assertEquals("wrong value", Symbol.CROSS, empty.getSymbol());

    }

    @Test(expected = IllegalArgumentException.class)
    public void negIndex() {
        MarkInterface mark = UTTTFactory.createMark(Symbol.CROSS, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void outIndex() {
        MarkInterface mark = UTTTFactory.createMark(Symbol.CROSS, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullSymbol() {
        MarkInterface empty = UTTTFactory.createMark(Symbol.EMPTY, 5);
        empty.setSymbol(null);
    }
}
