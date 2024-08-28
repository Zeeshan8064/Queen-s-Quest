package controller;

import boardgame.BoardGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class StartPageController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private static BoardGame model = new BoardGame();


    @FXML
    private TextField player1NameTextField;

    @FXML
    private TextField player2NameTextField;

    @FXML
    private Button exitGameButton;

    @FXML
    private Label warningLabel;

    String Player1Name;
    String Player2Name;

    /**
     * Switches to the model scene when the "Start Game" button is clicked.
     * Validates the player name input and initializes the model.
     * Saves Player and Game information to the Game Object
     *
     * @param event The action event triggered by clicking the "Start Game" button.
     * @throws IOException If an I/O error occurs while loading the model scene.
     */
    public void SwitchToGame(ActionEvent event) throws IOException {

        if(player1NameTextField.getText().isBlank() || player2NameTextField.getText().isBlank() ){
            warningLabel.setText("Please Input Your Name");
        }
        else {
            Player1Name = player1NameTextField.getText();
            Player2Name = player2NameTextField.getText();
            model.setPlayer1Name(Player1Name);
            model.setPlayer2Name(Player2Name);
            model.setStartDateTime(LocalDateTime.now());
            Parent root = FXMLLoader.load(getClass().getResource("/ui2.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Queen's Quest");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Switches to the high scores scene when the "High Scores" button is clicked.
     *
     * @param event The action event triggered by clicking the "High Scores" button.
     * @throws IOException If an I/O error occurs while loading the high scores scene.
     */
    public void showHighScores(ActionEvent event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("/highscores.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Queen's Quest");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Exits the model when the "Exit Game" button is clicked.
     *
     * @param event The action event triggered by clicking the "Exit Game" button.
     * @throws IOException If an I/O error occurs while closing the application window.
     */
    public void exitGame(ActionEvent event) throws IOException {
        Logger.info(model.getStartDateTime());
        Stage stage = (Stage) exitGameButton.getScene().getWindow();
        stage.close();
    }


}
