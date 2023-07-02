package Strategies.WinningStrategies;

import Models.Board;
import Models.Move;
import Models.Player;
import Models.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneDiagonalWinningStrategy implements WinningStrategy{
    private Map<Symbol, Integer> leftDiagMap;
    private Map<Symbol, Integer> rightDiagMap;

    public OrderOneDiagonalWinningStrategy(List<Player> players) {
        leftDiagMap = new HashMap<>();
        rightDiagMap = new HashMap<>();
        for (int i = 0; i < players.size(); i++) {
            leftDiagMap.put(players.get(i).getSymbol(), 0);
            rightDiagMap.put(players.get(i).getSymbol(), 0);
        }
    }
    public boolean checkWinner(Board board, Move move) {
        int row, col, dimension;
        Symbol symbol;
        row = move.getCell().getRow();
        col = move.getCell().getCol();
        dimension = board.getDimension();
        symbol = move.getPlayer().getSymbol();

        if((row != col) || (row + col != dimension-1))
            return false;

        if(row == col) {
            leftDiagMap.put(symbol, leftDiagMap.get(symbol) + 1);
        }

        if(row + col == dimension-1) {
            rightDiagMap.put(symbol, rightDiagMap.get(symbol) + 1);
        }

        if(row == col) {
            if (leftDiagMap.get(symbol) == dimension)
                return true;
        }

        if(row + col == dimension-1) {
            if(rightDiagMap.get(symbol) == dimension)
                return true;
        }
        return false;
    }

    public void handleUndo(Board board, Move move) {
        Symbol symbol = move.getPlayer().getSymbol();
        int row, col, dimension;
        row = move.getCell().getRow();
        col = move.getCell().getCol();
        dimension = board.getDimension();

        if(row == col) {
            leftDiagMap.put(symbol, leftDiagMap.get(symbol)-1);
        }

        if(row + col == dimension-1) {
            rightDiagMap.put(symbol, rightDiagMap.get(symbol)-1);
        }
    }
}
