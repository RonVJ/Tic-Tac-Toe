package Strategies.WinningStrategies;

import Models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderOneRowWinningStrategy implements WinningStrategy {
    private List<Map<Symbol, Integer>> rowMaps;

    public OrderOneRowWinningStrategy(int dimension, List<Player> players) {
        rowMaps = new ArrayList<>();
        Map map;
        for(int i = 0; i < dimension; i++) {
            map = new HashMap<>();
            for(int j = 0; j < players.size(); j++) {
                map.put(players.get(j).getSymbol(), 0);
            }
            rowMaps.add(map);
        }
    }
    public boolean checkWinner(Board board, Move move) {
        int row;
        Symbol symbol;

        symbol = move.getPlayer().getSymbol();
        row = move.getCell().getRow();

        rowMaps.get(row).put(symbol, rowMaps.get(row).get(symbol) + 1);

        if(rowMaps.get(row).get(symbol) == board.getDimension()) {
            return true;
        }

        return false;
    }

    public void handleUndo(Board board, Move move) {
        Symbol symbol = move.getPlayer().getSymbol();
        int row = move.getCell().getRow();
        rowMaps.get(row).put(symbol, rowMaps.get(row).get(symbol)-1);
    }


}
