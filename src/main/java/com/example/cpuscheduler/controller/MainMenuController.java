package com.example.cpuscheduler.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    private Stage stage;

    public static String currentStage;

    @FXML
    void handleFCFS(ActionEvent event) throws IOException{
        // thuật toán lựa chọn là FCFS
        currentStage = "FCFS";
        openStageSimulation("FCFS", event);
    }

    @FXML
    void handleSJF(ActionEvent event) throws IOException {
        // thuật toán lựa chọn là SJF
        currentStage = "SJF";
        openStageSimulation("SJF", event);
    }

    @FXML
    void handleRoundRobin(ActionEvent event) throws IOException {
        // thuật toán lựa chọn là RoundRobin
        currentStage = "Round Robin";
        openStageSimulation("Round Robin", event);
    }

    @FXML
    void handleHelp(){
        // giải thích thuật toán bằng alert information
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Algorithm Explain.");
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.getDialogPane().setPrefSize(500, 350);
        alert.setContentText("""
                FCFS (First-Come, First-Served) is a CPU scheduling algorithm where processes 
                are executed in the order they arrive. The first process to arrive is 
                executed first, without preemption. It is simple but can lead to the 
                "convoy effect," where shorter processes get delayed behind longer ones.
                
                SJF (Shortest Job First) is a CPU scheduling algorithm that selects the process 
                with the shortest burst time to execute next. It minimizes average waiting time 
                and improves efficiency but can cause starvation if shorter processes keep 
                arriving, delaying longer ones. It can be preemptive or non-preemptive.
                
                RR (Round Robin) is a CPU scheduling algorithm that assigns a fixed time slice 
                (quantum) to each process in a cyclic order. If a process doesn't finish within 
                its time slice, it is moved to the end of the queue. This ensures fair CPU 
                allocation and reduces waiting time but may increase context switching overhead.
                """);
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.YES){
                System.out.println("yes");
            }else{
                System.out.println("no");
            }
        });
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit program?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText(null);

            if (alert.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
                e.consume();
            }
        });
    }

    private void setupCloseConfirmation(Stage stage) throws IOException {
        // hộp thoại xác nhận đóng chương trình
        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit program?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText(null);

            if (alert.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
                event.consume();
            }
        });
    }

    public void closeStage(Stage stage) throws IOException {
        // truyền method cho application
        this.stage = stage;
        setupCloseConfirmation(stage);
    }

}
