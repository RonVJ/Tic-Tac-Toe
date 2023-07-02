package Models;

import Strategies.WinningStrategies.WinningStrategy;

import java.util.*;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private List<WinningStrategy> winningStrategies;
    private int currentPlayerIndex;
    private GameStatus gameStatus;
    private Player winner;
    private Game (GameBuilder gameBuilder) {
        this.board = new Board(gameBuilder.getDimension());
        this.players = gameBuilder.getPlayers();
        this.moves = new ArrayList<>();
        this.winningStrategies = gameBuilder.getWinningStrategies();
        this.currentPlayerIndex = 0;
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.winner = null;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void displayBoard() {
        board.display();
    }

    private boolean validateMove(Cell cell) {
        int row, col, dimension;
        row = cell.getRow();
        col = cell.getCol();
        dimension = board.getDimension();

        if(row < 0
                || col < 0
                || row >= dimension
                || col >= dimension) {
            return false;
        }

        return board.getGrid().get(row).get(col).getCellState().equals(CellState.EMPTY);
    }

    private boolean checkWinner(Move move) {
        for (WinningStrategy winningStrategy : winningStrategies) {
            if(winningStrategy.checkWinner(board, move)) {
                gameStatus = GameStatus.ENDED;
                winner = players.get(currentPlayerIndex);
                return true;
            }
        }
        return false;
    }

    private boolean checkDraw() {
        int dimension = board.getDimension();
        if (moves.size() == dimension * dimension) {
            gameStatus = GameStatus.DRAW;
            return true;
        }
        return false;
    }

    public void makeMove() {
        Player currentPlayer;
        Cell proposedCell, cellInGrid;
        Move move;
        int numPlayers;

        numPlayers = players.size();
        currentPlayer = players.get(currentPlayerIndex);

        System.out.println("It is " + currentPlayer.getName() + "'s turn.");

        proposedCell = currentPlayer.makeMove(board);

        System.out.println("Move made at row: " + proposedCell.getRow() +
                " col: " + proposedCell.getCol() + ".");

        if(!validateMove(proposedCell)) {
            System.out.println("Invalid move, try again!");
            return;
        }

        cellInGrid = board.getGrid().get(proposedCell.getRow()).get(proposedCell.getCol());
        cellInGrid.setCellState(CellState.FILLED);
        cellInGrid.setPlayer(currentPlayer);
        move = new Move(currentPlayer, cellInGrid);
        moves.add(move);

        if(checkWinner(move)) {
            return;
        }

        if(checkDraw()) {
            return;
        }

        currentPlayerIndex = (currentPlayerIndex + 1)%numPlayers;
    }

    public void undo() {
        if(moves.size() == 0) {
            System.out.println("No moves made yet. Can not Undo!");
            return;
        }

        //cell
        //remove move
        //handleUndo in WinningStrategy
        //currentPlayerIndex

        Move lastMove = moves.get(moves.size()-1);
        Cell cell = lastMove.getCell();
        int numPlayers = players.size();

        for (WinningStrategy winningStrategy : winningStrategies) {
            winningStrategy.handleUndo(board, lastMove);
        }

        cell.setPlayer(null);
        cell.setCellState(CellState.EMPTY);

        moves.remove(moves.size()-1);

        currentPlayerIndex = (currentPlayerIndex - 1 + numPlayers)%numPlayers;
    }

    public void printResult() {
        if(gameStatus.equals(GameStatus.DRAW)) {
            System.out.println("The game is drawn!");
        }
        else if(gameStatus.equals(GameStatus.ENDED)) {
            System.out.println("The game has ended!");
            System.out.println("The winner is " + winner.getName());
        }
    }

    public static GameBuilder getGameBuilder() {
        return new GameBuilder();
    }

    public static class GameBuilder {
        private int dimension;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        public List<Player> getPlayers() {
            return players;
        }

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public int getDimension() {
            return dimension;
        }

        public GameBuilder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public List<WinningStrategy> getWinningStrategies() {
            return winningStrategies;
        }

        public GameBuilder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        private boolean valid() {
            int numPlayers, numBotPlayers;
            Set<Character> existingSymbols = new HashSet<>();

            numPlayers = players.size();

            if (numPlayers < 2) {
                return false;
            }

            if (numPlayers != dimension-1) {
                return false;
            }

            numBotPlayers = 0;
            for(Player player : players) {
                if(player.getPlayerType().equals(PlayerType.BOT))
                    numBotPlayers++;
            }
            if(numBotPlayers > 1) {
                return false;
            }

            for(Player player: players) {
                if(existingSymbols.contains(player.getSymbol().getaChar())) {
                    return false;
                }

                existingSymbols.add(player.getSymbol().getaChar());
            }


            return true;
        }

        public Game build() throws Exception {
            if(!valid()) {
                throw new Exception("Game params invalid");
            }
            return new Game(this);
        }
    }
}

