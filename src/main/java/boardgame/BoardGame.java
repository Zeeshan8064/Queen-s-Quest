package boardgame;

import game.TwoPhaseMoveState;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.tinylog.Logger;

import java.time.LocalDateTime;
import java.util.Random;

public class BoardGame implements TwoPhaseMoveState<Position> {

    @Getter
    public static final int BOARD_ROW = 8;
    @Getter
    public static final int BOARD_COL = 8;
    private Player player;

    @Setter
    @Getter
    @NonNull
    private static LocalDateTime startDateTime;

    @Getter
    private  String winner;


    private ReadOnlyIntegerWrapper numberOfMovesplayer1= new ReadOnlyIntegerWrapper(0);
    private ReadOnlyIntegerWrapper numberOfMovesplayer2= new ReadOnlyIntegerWrapper(0);

    private static final ReadOnlyStringWrapper player1Name = new ReadOnlyStringWrapper("");
    private static final ReadOnlyStringWrapper player2Name = new ReadOnlyStringWrapper("");


    private final ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_ROW][BOARD_COL];



    /**
     * Constructs a ChessGameModel object and initializes the chess board.
     */
    public BoardGame() {
        for (var i = 0; i < BOARD_ROW; i++) {
            for (var j = 0; j < BOARD_COL; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<>(Square.NONE);
            }
        }
        placeWhiteQueen();
    }

    private void placeWhiteQueen() {
        Random random = new Random();
        boolean placeInTopRow = random.nextBoolean();

        if (placeInTopRow) {
            int column = random.nextInt(BOARD_COL);
            board[0][column].set(Square.WHITE_QUEEN);
        } else {
            int row = random.nextInt(BOARD_ROW);
            board[row][BOARD_COL - 1].set(Square.WHITE_QUEEN);
        }
        player = Player.PLAYER_1;
    }

    /**
     * Returns the read-only property for the square at the specified position.
     *
     * @param i the row index of the position
     * @param j the column index of the position
     * @return the read-only property for the square at the specified position
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * Returns the square at the specified position.
     *
     * @param p the position
     * @return the square at the specified position
     */
    public Square getSquare(Position p) {
        return board[p.row()][p.col()].get();
    }

    /**
     * Sets the square at the current position
     *
     * @param p the position
     * @param square the square
     */
    private void setSquare(Position p, Square square) {
        board[p.row()][p.col()].set(square);
    }

    public boolean canMove(Position from, Position to) {
        return isOnBoard(from) && isOnBoard(to) && !isEmpty(from) && isEmpty(to) && isLegalMove(from, to);


    }
    public static boolean isOnBoard(Position p) {
        return 0 <= p.row() && p.row() < BOARD_ROW && 0 <= p.col() && p.col() < BOARD_COL;
    }

    @Override
    public boolean isLegalToMoveFrom(Position from) {
        if (!isOnBoard(from)) {
            return false;
        }
        return getSquare(from) == Square.WHITE_QUEEN;
    }

    @Override
    public boolean isLegalMove(Position from, Position to) {
        int rowDiff = to.row() - from.row();
        int colDiff = to.col() - from.col();

        // Ensure the move is down or left or diagonally left-down
        return (rowDiff > 0 && colDiff == 0) || // down
                (rowDiff == 0 && colDiff < 0) || // left
                (rowDiff > 0 && colDiff < 0 && rowDiff == -colDiff); // diagonally left-down
    }

    @Override
    public void makeMove(Position from, Position to) {
        setSquare(to, getSquare(from));
        setSquare(from, Square.NONE);
        // Switch player
        increaseMoves(String.valueOf(player));
        player = player.opponent();
        //player = (player == Player.PLAYER_1) ? Player.PLAYER_2 : Player.PLAYER_1;
    }

    public void increaseMoves( String name){
        if(player.equals(Player.PLAYER_1)){
            numberOfMovesplayer1.set(numberOfMovesplayer1.get()+1);

        }
        else{
            numberOfMovesplayer2.set(numberOfMovesplayer2.get()+1);
        }
    }


    @Override
    public Player getNextPlayer() {
        return player;
    }

    @Override
    public boolean isGameOver() {
        return getSquare(new Position(BOARD_ROW - 1, 0)) == Square.WHITE_QUEEN;
    }

    @Override
    public Status getStatus() {
        if (!isGameOver()) {
            return Status.IN_PROGRESS;
        }
        return player == Player.PLAYER_2 ? Status.PLAYER_1_WINS : Status.PLAYER_2_WINS;
    }
    public void setWinner(){
        if (isWinner(Player.PLAYER_1)) {
            winner = getPlayer1Name();
        } else if (isWinner(Player.PLAYER_2)) {
            winner = getPlayer2Name();        }

    }

    /**
     * Checks if a position on the chess board is empty.
     *
     * @param position the position to check
     * @return true if the position is empty, false otherwise
     */
    public boolean isEmpty(Position position) {
        return getSquare(position) == Square.NONE;
    }



    public void setPlayer1Name(String name) { player1Name.set(name); }

    public void setPlayer2Name(String name) { player2Name.set(name); }

    public String getPlayer1Name() {  return player1Name.get(); }

    public String getPlayer2Name() { return player2Name.get(); }

    public ReadOnlyStringProperty nameofPlayer1Property(){ return player1Name.getReadOnlyProperty(); }
    public ReadOnlyStringProperty nameofPlayer2Property(){ return player2Name.getReadOnlyProperty(); }

    public ReadOnlyIntegerProperty numberOfMovesPlayer1Property(){return numberOfMovesplayer1.getReadOnlyProperty();}


    public ReadOnlyIntegerProperty numberOfMovesPlayer2Property(){ return numberOfMovesplayer2.getReadOnlyProperty(); }




    public String toString() {
        var sb = new StringBuilder();
        for (var i = 0; i < BOARD_ROW; i++) {
            for (var j = 0; j < BOARD_COL; j++) {
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
    /**
     * Main method to test the BoardGame class.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        var model = new BoardGame();
        System.out.println(model);
    }
}
