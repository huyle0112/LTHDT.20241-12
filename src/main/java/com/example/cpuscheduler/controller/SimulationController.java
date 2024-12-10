package com.example.cpuscheduler.controller;

import com.example.cpuscheduler.model.Process;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {

    @FXML
    public TextField arrivalTimeTextFiled;

    @FXML
    public Label priorityLabel;

    @FXML
    private Pane pane;
    @FXML
    private Label quantumTimeLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField quanTumTextField;

    @FXML
    private Button addButton;

    @FXML
    private TextField burstTimeTextField;

    @FXML
    private TextField arrivalTimeTextField;

    @FXML
    private TableColumn<Process, Integer> priorityColumn;

    @FXML
    private TableColumn<Process, Double> arrivalTimeColumn;

    @FXML
    private TextField priorityTextField;

    @FXML
    private TableColumn<Process, Integer> idColumn;

    @FXML
    private TableColumn<Process, Double> burstTimeColumn;

    @FXML
    private TableView<Process> table;

    @FXML
    private Button deleteAllButton;

    private ObservableList<Process> processList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        processList = FXCollections.observableArrayList(
                new Process(1, 0.0,5.0,0),
                new Process(2, 0.0,6.0,0),
                new Process(3, 7.0, 8.0, 0)
        );
        idColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("id"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Double>("arrivalTime"));
        burstTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Double>("burstTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("priority"));
        table.setItems(processList);

        double[] timestamps = {1, 2, 3, 5};

        SequentialTransition sequence = new SequentialTransition();
        int index = 0;
        for (double timestamp : timestamps) {
            PauseTransition pause = new PauseTransition(Duration.seconds(timestamp));
            int k = index;
            pause.setOnFinished(event -> {
                Rectangle square = createSquare();


                square.setX(k * 60);
                square.setY(0);


                pane.getChildren().add(square);


                TranslateTransition move = new TranslateTransition(Duration.seconds(1), square);
                move.setByY(50);
                move.play();
            });

            sequence.getChildren().add(pause);

            index++;
        }

        sequence.play();

        if(MainMenuController.currentStage.equals("FCFS")){
            quantumTimeLabel.setVisible(false);
            quanTumTextField.setVisible(false);
            priorityColumn.setVisible(false);
            priorityTextField.setVisible(false);
            priorityLabel.setVisible(false);
            idColumn.setPrefWidth(idColumn.getWidth() + 24);
            burstTimeColumn.setPrefWidth(burstTimeColumn.getWidth() + 50);
            arrivalTimeColumn.setPrefWidth(arrivalTimeColumn.getWidth() + 50);
        }

        if(MainMenuController.currentStage.equals("SJN")){
            quantumTimeLabel.setVisible(false);
            quanTumTextField.setVisible(false);
        }

        if(MainMenuController.currentStage.equals("Round Robin")){
            priorityColumn.setVisible(false);
            priorityTextField.setVisible(false);
            priorityLabel.setVisible(false);
            idColumn.setPrefWidth(idColumn.getWidth() + 24);
            burstTimeColumn.setPrefWidth(burstTimeColumn.getWidth() + 50);
            arrivalTimeColumn.setPrefWidth(arrivalTimeColumn.getWidth() + 50);
        }
    }


    private Rectangle createSquare() {
        Rectangle square = new Rectangle(50, 50, 50, 50);
        square.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        return square;
    }

    public void handleAdd(ActionEvent actionEvent) {
        Process newProcess = new Process();
        newProcess.setArrivalTime(Double.parseDouble(arrivalTimeTextField.getText()));
        newProcess.setBurstTime(Double.parseDouble(burstTimeTextField.getText()));
        newProcess.setPriority(Integer.parseInt(priorityTextField.getText()));
        processList.add(newProcess);
    }

    public void handleDelete(ActionEvent actionEvent) {
        ObservableList<Process> selectedProcess = table.getSelectionModel().getSelectedItems();
        for (Process process : processList) {
            if (selectedProcess.contains(process)) {
                processList.remove(process);
            }
        }
    }

    public void handleDeleteAll(ActionEvent actionEvent) {
        processList.clear();
    }
}

