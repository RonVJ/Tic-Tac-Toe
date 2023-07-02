package Strategies.WinningStrategies;

import Models.Board;
import Models.Move;
import Models.Player;
import Models.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneColumnWinningStrategy implements WinningStrategy {
    private List<Map<Symbol, Integer>> colMaps;

    public OrderOneColumnWinningStrategy(int dimension, List<Player> players) {
        colMaps = new ArrayList<>();
        Map map;
        for(int i = 0; i < dimension; i++) {
            map = new HashMap<>();
            for(int j = 0; j < players.size(); j++) {
                map.put(players.get(j).getSymbol(), 0);
            }
            colMaps.add(map);
        }
    }
    public boolean checkWinner(Board board, Move move) {
        int col;
        Symbol symbol;

        symbol = move.getPlayer().getSymbol();
        col = move.getCell().getCol();

        colMaps.get(col).put(symbol, colMaps.get(col).get(symbol) + 1);

        if(colMaps.get(col).get(symbol) == board.getDimension()) {
            return true;
        }

        return false;
    }

    public void handleUndo(Board board, Move move) {
        Symbol symbol = move.getPlayer().getSymbol();
        int col = move.getCell().getRow();
        colMaps.get(col).put(symbol, colMaps.get(col).get(symbol)-1);
    }
}
