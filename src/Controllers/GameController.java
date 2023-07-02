package Controllers;

import Models.Game;
import Models.GameStatus;
import Models.Move;
import Models.Player;
import Strategies.WinningStrategies.WinningStrategy;

import java.util.List;

public class GameController {
    public Game createGame(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) throws Exception {
        return Game.getGameBuilder()
                .setDimension(dimension)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public GameStatus getGameStatus(Game game) {
        return game.getGameStatus();
    }

    public void displayBoard(Game game) {
        game.displayBoard();
    }

    public void undo(Game game) {
        game.undo();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public void printResult(Game game) {
        game.printResult();
    }
}
