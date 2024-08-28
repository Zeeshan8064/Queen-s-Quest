package controller;

import ResultModel.JsonResultManager;
import ResultModel.Stats;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class HighScoreController {

    public Button Exit;


    @FXML
    private TableColumn<Stats, String> playerNameColumn;

    @FXML
    private TableView<Stats> Table;

    @FXML
    private TableColumn<Stats, Integer> winsColumn;

    private Stage stage;
    private Scene scene;

    @FXML
    void handleExit(ActionEvent event) {
        Platform.exit();

    }
    @FXML
    public void initialize() throws IOException {
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("WinnerName"));
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        ObservableList<Stats> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(new JsonResultManager(Path.of("results.json")).getBestPlayers(5));
        Table.setItems(observableResult);
    }


}
