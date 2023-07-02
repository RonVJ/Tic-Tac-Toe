package Strategies.WinningStrategies;

import Models.Board;
import Models.Move;

public interface WinningStrategy {
    boolean checkWinner(Board board, Move move);
    void handleUndo(Board board, Move move);
}
