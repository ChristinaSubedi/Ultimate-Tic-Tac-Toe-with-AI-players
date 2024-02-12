package uttt.game;

import uttt.UTTTFactory;
import uttt.utils.Move;
import uttt.utils.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerInterfaceImpl implements PlayerInterface {

    private Symbol currentSymbol;
    private Boolean isAi;

    public PlayerInterfaceImpl(Symbol symbol, Boolean isAI) {
        this.currentSymbol = symbol;
        this.isAi = isAI;
    }

    @Override
    public Symbol getSymbol() {
        return this.currentSymbol;
    }

    @Override
    public Move getPlayerMove(SimulatorInterface game, UserInterface ui) throws IllegalArgumentException {
        if (!isAi) {
            Move nextMove = ui.getUserMove();
            while (!game.isMovePossible(nextMove.getBoardIndex(), nextMove.getMarkIndex())) {
                nextMove = ui.getUserMove();
            }

            return nextMove;
        } else {
            SimulatorInterface clonedGame = cloneGame(game, game.getIndexNextBoard());
            Move bestMove = getBestMove(clonedGame);
            if (!game.isMovePossible(bestMove.getBoardIndex(), bestMove.getMarkIndex())) {
                throw new IllegalArgumentException("move not possible");
            }
            return bestMove;
        }
    }

    // implemented using chatgpt, gave all functions and prompt was "write an
    // autoplayer that places the currentsymbol in the board, then checks if it
    // forms two in a row or three in a row, or just one in a row, assign scores,
    // and select move with highest score" followed by asking it to randomise the
    // moves

    private Move getBestMove(SimulatorInterface game) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;
        int storeLastIndex = game.getIndexNextBoard();

        for (int boardIndex = 0; boardIndex < 9; boardIndex++) {
            if (!game.isMovePossible(boardIndex)) {
                continue; // Skip invalid boards
            }

            List<Integer> markIndices = new ArrayList<>();
            for (int markIndex = 0; markIndex < 9; markIndex++) {
                if (game.isMovePossible(boardIndex, markIndex)) {
                    markIndices.add(markIndex);
                }
            }

            Collections.shuffle(markIndices); // Shuffle the order of mark indices

            for (int markIndex : markIndices) {

                game.setMarkAt(currentSymbol, boardIndex, markIndex); // Place the current symbol in the board

                int score = evaluateBoard(game, currentSymbol, boardIndex); // Evaluate the board
                game.setIndexNextBoard(storeLastIndex);
                game.setCurrentPlayerSymbol(Symbol.EMPTY);
                game.setMarkAt(Symbol.EMPTY, boardIndex, markIndex);
                game.setIndexNextBoard(storeLastIndex);
                game.setCurrentPlayerSymbol(currentSymbol);

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = new Move(boardIndex, markIndex);
                }

            }
        }

        if (bestMove == null) {
            throw new IllegalArgumentException("No available move found.");
        }

        return bestMove;
    }

    private int evaluateBoard(SimulatorInterface game, Symbol symbol, int boardIndex) {
        int score = 0;

        // Check for three in a row
        if (game.getBoards()[boardIndex].getWinner() == symbol) {
            score += 10000;
            return score;
        }

        // Check for two in a row with an empty spot
        int[][] winningCombinations = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // Rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // Columns
                { 0, 4, 8 }, { 2, 4, 6 } // Diagonals
        };

        for (int[] combination : winningCombinations) {
            int count = 0;
            int opponenntCount = 0;
            int emptySpotIndex = -1;
            for (int markIndex : combination) {
                if (game.getBoards()[boardIndex].getMarks()[markIndex].getSymbol() == symbol) {
                    count++;
                } else if (game.getBoards()[boardIndex].getMarks()[markIndex].getSymbol() == Symbol.EMPTY) {
                    emptySpotIndex = markIndex;
                } else {
                    opponenntCount++;
                }
            }
            if (count == 2 && emptySpotIndex != -1) {
                score += 500;
            } else if (count == 1 && opponenntCount == 2) {
                score += 600;
            }
        }

        // Check for one in a row
        if (score == 0) {
            return 10;
        }

        return score;
    }

    private SimulatorInterface cloneGame(SimulatorInterface game, int lastSymbolIndex) {
        SimulatorInterface clonedGame = UTTTFactory.createSimulator();
        BoardInterface[] clonedBoards = { UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                UTTTFactory.createBoard() };
        BoardInterface[] originalBoards = game.getBoards();

        for (int boards = 0; boards < 9; boards++) {
            for (int mark = 0; mark < 9; mark++) {
                clonedBoards[boards].setMarkAt(originalBoards[boards].getMarks()[mark].getSymbol(), mark);
            }

        }
        clonedGame.setBoards(clonedBoards);
        clonedGame.setCurrentPlayerSymbol(currentSymbol);
        clonedGame.setIndexNextBoard(lastSymbolIndex);

        return clonedGame;
    }

}
