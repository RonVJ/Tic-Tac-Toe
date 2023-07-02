import Controllers.GameController;
import Models.*;
import Strategies.WinningStrategies.OrderOneColumnWinningStrategy;
import Strategies.WinningStrategies.OrderOneDiagonalWinningStrategy;
import Strategies.WinningStrategies.OrderOneRowWinningStrategy;
import Strategies.WinningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Tic Tac Toe Application
public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        int dimension;
        Player player;
        Game game;

        dimension = 3;
        List<Player> players = List.of(new Player("Amit", new Symbol('X'), PlayerType.HUMAN)
        , new Player("Rohan", new Symbol('O'), PlayerType.HUMAN));

        List<WinningStrategy> winningStrategies = List.of(new OrderOneDiagonalWinningStrategy(players)
        , new OrderOneRowWinningStrategy(dimension, players)
        , new OrderOneColumnWinningStrategy(dimension, players));

        Scanner scanner = new Scanner(System.in);

        try {
            game = gameController.createGame(
                    dimension,
                    players,
                    winningStrategies
                );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("-------------- Game is starting --------------");
        while(gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)) {
            System.out.println("This is how board looks like:");
            gameController.displayBoard(game);
            System.out.printf("Do you want to undo (y/n): ");
            if (scanner.next().equalsIgnoreCase("y")) {
                gameController.undo(game);
            }
            else {
                gameController.makeMove(game);
            }
        }

        gameController.printResult(game);
        gameController.displayBoard(game);
    }
}
