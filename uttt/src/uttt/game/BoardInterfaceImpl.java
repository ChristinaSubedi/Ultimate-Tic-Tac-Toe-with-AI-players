package uttt.game;

import uttt.utils.Symbol;

public class BoardInterfaceImpl implements BoardInterface {

    private MarkInterface[] marks;

    public BoardInterfaceImpl() {
        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = new MarkInterfaceImpl(Symbol.EMPTY, i);
        }
    }

    public MarkInterface[] getMarks() {
        return this.marks;
    }

    public void setMarks(MarkInterface[] marks) throws IllegalArgumentException {
        if (marks == null || marks.length != 9) {
            throw new IllegalArgumentException();
        }
        this.marks = marks;
    }

    public boolean setMarkAt(Symbol symbol, int markIndex) throws IllegalArgumentException {
        if (symbol == null || markIndex < 0 || markIndex > 8) {
            throw new IllegalArgumentException();
        }

        if (this.isClosed()) {
            return false;
        }

        this.marks[markIndex].setSymbol(symbol);
        return true;

    }

    // Used chatGPT to generate, input prompt was how to check for winner if
    // the function implements a tic tac toe, and gave all required functions as
    // input
    public Symbol getWinner() {
        int[][] winningCombinations = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // Rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // Columns
                { 0, 4, 8 }, { 2, 4, 6 } // Diagonals
        };

        for (int[] combination : winningCombinations) {
            MarkInterface mark1 = marks[combination[0]];
            MarkInterface mark2 = marks[combination[1]];
            MarkInterface mark3 = marks[combination[2]];

            Symbol symbol1 = mark1.getSymbol();
            Symbol symbol2 = mark2.getSymbol();
            Symbol symbol3 = mark3.getSymbol();

            if (symbol1 != Symbol.EMPTY &&
                    symbol1 == symbol2 &&
                    symbol1 == symbol3) {
                return symbol1; // Winning symbol found
            }
        }

        return Symbol.EMPTY; // No winner, return empty symbol
    }

    public boolean isClosed() {
        if (getWinner() != Symbol.EMPTY) {
            return true;
        }

        for (int i = 0; i < 9; i++) {
            if (marks[i].getSymbol() == Symbol.EMPTY) {
                return false;
            }
        }

        return true;
    }

    public boolean isMovePossible(int markIndex) throws IllegalArgumentException {
        if (markIndex > 8 || markIndex < 0) {
            throw new IllegalArgumentException();
        }

        if (this.isClosed()) {
            return false;
        }

        if (this.isClosed() || this.marks[markIndex].getSymbol() != Symbol.EMPTY) {
            return false;
        }

        return true;

    }

}
