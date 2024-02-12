package uttt.game;

import uttt.utils.Symbol;

public class MarkInterfaceImpl implements MarkInterface {

    private Symbol symbol;
    private int position;

    public MarkInterfaceImpl(Symbol symbol, int j) {

        if (symbol == null || j < 0 || j > 8) {
            throw new IllegalArgumentException("symbol is null");
        }

        this.symbol = symbol;
        this.position = j;
    }

    public Symbol getSymbol() {
        return this.symbol;

    }

    public int getPosition() {
        return this.position;
    }

    public void setSymbol(Symbol symbol) throws IllegalArgumentException {
        if (symbol == null) {
            throw new IllegalArgumentException("symbol is null");
        }

        this.symbol = symbol;
    }

    public MarkInterfaceImpl clone() {
        return new MarkInterfaceImpl(this.symbol, this.position);
    }

}
