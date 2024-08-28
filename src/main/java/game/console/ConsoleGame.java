package game.console;

import boardgame.BoardGame;
import boardgame.Position;
import game.console.TwoPhaseMoveGame;

import java.util.Scanner;

/**
 * Conducts the dummy game on the console.
 */
public class ConsoleGame {

    public static void main(String[] args) {
        var state = new BoardGame();
        var game = new TwoPhaseMoveGame<Position>(state, ConsoleGame::parseMove);
        game.start();
    }

    /**
     * Converts a string containing the position of a move to a {@code Position}
     * object.
     *
     * @param s a string that should contain two space-separated integers
     * @return th0e {@code Position} object that was constructed from the string
     * @throws IllegalArgumentException if the format of the string is invalid,
     * i.e., its content is not two integers separated with spaces
     */
    public static Position parseMove(String s) {
        s = s.trim();
        if (!s.matches("\\d+\\s+\\d+")) {
            throw new IllegalArgumentException();
        }
        var scanner = new Scanner(s);
        return new Position(scanner.nextInt(), scanner.nextInt());
    }

}