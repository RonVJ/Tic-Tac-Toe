package Models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int dimension;
    private List<List<Cell>> grid;

    public Board(int dimension) {
        this.dimension = dimension;
        grid = new ArrayList<>();
        for(int i = 0; i < dimension; i++) {
            grid.add(new ArrayList<>());
            for(int j = 0; j < dimension; j++) {
                grid.get(i).add(new Cell(i, j));
            }
        }
    }
    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public List<List<Cell>> getGrid() {
        return grid;
    }

    public void setGrid(List<List<Cell>> grid) {
        this.grid = grid;
    }

    public void display() {
        Player player = null;
        for (List<Cell> row : grid) {
            System.out.print("| ");
            for (Cell cell : row) {
                player = cell.getPlayer();
                if(player == null) {
                    System.out.print("- |");
                }
                else {
                    System.out.printf(player.getSymbol().getaChar() + " |");
                }
            }
            System.out.println();
        }
    }
}
