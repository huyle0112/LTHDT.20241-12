package com.example.cpuscheduler.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button buttonRoundRobin;

    @FXML
    private Button buttonFCFS;

    @FXML
    private Button buttonSJN;

    @FXML
    private Button buttonHelp;

    private Stage stage;

    public static String currentStage;

    @FXML
    void handleFCFS(ActionEvent event) throws IOException{
        currentStage = "FCFS";
        openStageSimulation("FCFS", event);
    }

    @FXML
    void handleSJN(ActionEvent event) throws IOException {
        currentStage = "SJN";
        openStageSimulation("SJN", event);
    }

    @FXML
    void handleRoundRobin(ActionEvent event) throws IOException {
        currentStage = "Round Robin";
        openStageSimulation("Round Robin", event);
    }

    @FXML
    void handleHelp(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Giải thích thuật toán");
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.setContentText("hello");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.YES){
                System.out.println("yes");
            }else{
                System.out.println("no");
            }
        });
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public void openStageSimulation(String title, ActionEvent event) throws IOException {
        // Đóng Stage main
        Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Mở Stage simulation
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/cpuscheduler/SimulationView.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle(title + " simulation");
        newStage.setResizable(false);
        newStage.show();
        newStage.setOnCloseRequest(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Thoát " + MainMenuController.currentStage + " simulation?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Exit simulation Confirmation");
            alert.setHeaderText(null);

            if (alert.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
                event.consume();
            }else{
                currentStage.show();
            }
        });
    }

    private void setupCloseConfirmation(Stage stage) throws IOException {
        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Thoát chương trình?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText(null);

            if (alert.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
                event.consume();
            }
        });
    }

    public void closeStage(Stage stage) throws IOException {
        this.stage = stage;
        setupCloseConfirmation(stage);
    }

}
