package controller;

import ResultModel.JsonResultManager;
import ResultModel.Result;
import ResultModel.ResultManager;
import boardgame.BoardGame;
import boardgame.BoardGameMoveSelector;
import boardgame.Position;
import boardgame.Square;
import game.State;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.tinylog.Logger;


import java.io.IOException;
import java.nio.file.Path;


import static boardgame.BoardGameMoveSelector.Phase;


public class BoardGameController {


    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    ResultManager manager = new JsonResultManager(Path.of("results.json"));

    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    private Label player1NameLabel;

    @FXML
    private Label player2NameLabel;

    @FXML
    private Label quitButtonMessage;

    @FXML
    private TextField player1moves;

    @FXML
    private TextField player2moves;

    @FXML
    private TextField PlayerTurn;

    @FXML
    private GridPane board;

    private BoardGame model = new BoardGame();

    private BoardGameMoveSelector selector = new BoardGameMoveSelector(model);

    /**
     * Initializes the Board Game controller.
     * Sets up the initial state of the game board and handles phase changes.
     */
    @FXML
    private void initialize() {
        player1NameLabel.setText((model.getPlayer1Name()));
        player2NameLabel.setText((model.getPlayer2Name()));
        player1moves.textProperty().bind(model.numberOfMovesPlayer1Property().asString());
        player2moves.textProperty().bind(model.numberOfMovesPlayer2Property().asString());
        PlayerTurn.textProperty().bind(model.nameofPlayer1Property());
        for (var i = 0; i < model.getBOARD_ROW(); i++) {
            for (var j = 0; j < model.getBOARD_COL(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        selector.phaseProperty().addListener(this::showSelectionPhaseChange);
    }


    /**
     * Creates a square on the game board with the given position.
     *
     * @param i The row index of the square.
     * @param j The column index of the square.
     * @return The created StackPane representing the square.
     */
    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");

        // Create an ImageView with the desired image
        ImageView imageView = new ImageView();

        // Bind the imageProperty of the ImageView based on the square property
        imageView.imageProperty().bind(createSquareBinding(model.squareProperty(i, j)));

        square.getChildren().add(imageView);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }


    /**
     * Creates an ObjectBinding for the square image based on the square property.
     *
     * @param squareProperty The ReadOnlyObjectProperty representing the square.
     * @return The ObjectBinding for the square image.
     */
    private ObjectBinding<Image> createSquareBinding(ReadOnlyObjectProperty<Square> squareProperty) {
        return new ObjectBinding<>() {
            {
                super.bind(squareProperty);
            }

            @Override
            protected Image computeValue() {
                switch (squareProperty.get()) {
                    case NONE:
                        return null; // No image for NONE state
                    case WHITE_QUEEN:
                        return new Image("/Images/queen.png"); // Set the path to white image
                }
                return null; // Default case, no image
            }
        };
    }

    /**
     * Handles the mouse click event on a game board square.
     * Updates the selected position and checks if a move can be made.
     * Checks if the game has reached its Win or Lose State
     *
     * @param event The MouseEvent representing the mouse click event.
     */
    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        Logger.info("Click on square ({},{})", row, col);
        selector.select(new Position(row, col));
//        selector.checkResetPhase(new Position(row,col));
        if (selector.isReadyToMove()) {
            selector.makeMove();
            HandleGameOver();
        }

        if (model.getNextPlayer() == State.Player.PLAYER_1){
            PlayerTurn.textProperty().bind(model.nameofPlayer1Property());
        }else {
            PlayerTurn.textProperty().bind(model.nameofPlayer2Property());
        }
    }

    /**
     * Handles the phase change event in the move selector.
     * Updates the UI based on the new phase.
     *
     * @param value    The ObservableValue representing the new phase.
     * @param oldPhase The old phase.
     * @param newPhase The new phase.
     */
    private void showSelectionPhaseChange(ObservableValue<? extends Phase> value, Phase oldPhase, Phase newPhase) {
        switch (newPhase) {
            case SELECT_FROM -> {}
            case SELECT_TO -> {showSelection(selector.getFrom());
                possibleMoves(selector.getFrom());}
            case READY_TO_MOVE -> {hideSelection(selector.getFrom());
                hidePossibleMoves();
            }
        }
    }

    /**
     * Displays the possible moves from the given position on the game board.
     *
     * @param position The position from which to display possible moves.
     */
    private void possibleMoves(Position position){
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                if(model.canMove(position,(new Position(i,j)))){
                    var square = getSquare(new Position(i,j));
                    square.getStyleClass().add("allowed");
                }
            }
        }
    }

    /**
     * Hides the possible moves displayed on the game board.
     */
    private void hidePossibleMoves() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = getSquare(new Position(i, j));
                square.getStyleClass().remove("allowed");
            }
        }
    }


    /**
     * Displays the selection effect on the square at the given position.
     *
     * @param position The position of the selected square.
     */
    private void showSelection(Position position) {
        var square = getSquare(position);
        square.getStyleClass().add("selected");
    }

    /**
     * Hides the selection effect on the square at the given position.
     *
     * @param position The position of the deselected square.
     */
    private void hideSelection(Position position) {
        var square = getSquare(position);
        square.getStyleClass().remove("selected");
    }

    /**
     * Retrieves the StackPane representing the square at the given position on the game board.
     *
     * @param position The position of the square.
     * @return The StackPane representing the square.
     * @throws AssertionError if the square is not found.
     */
    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }


    /**
     * Handles the game over scenario by displaying an alert dialog.
     * Checks if the game is won or lost and shows the appropriate message.
     */
    public void HandleGameOver(){

        if(model.isGameOver()){
            model.setWinner();
            quitButtonMessage.setText("To Check Updated HighScores Press =>");
            alert.setTitle("Game Won");
            alert.setHeaderText(null);
            alert.setContentText("\t\tCongratulations!  "+ model.getWinner() +"\n\nYou won the game in .\n");
            // Display the alert dialog
            alert.showAndWait();
            board.setDisable(true);
            try {
                manager.add(createGameResult());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private  Result createGameResult() {
        Logger.info(model.getStartDateTime());
        return Result.builder()
                .Player1name(model.getPlayer1Name())
                .Player2name(model.getPlayer2Name())
                .numberOfMovesPlayer1(model.numberOfMovesPlayer1Property().get())
                .numberOfMovesPlayer2(model.numberOfMovesPlayer2Property().get())
                .winner(model.getWinner())
                .startDateTime(model.getStartDateTime())
                .build();
    }



    public void showHighScores(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/highscores.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Queen's Quest");
        stage.setScene(scene);
        stage.show();
    }

}

