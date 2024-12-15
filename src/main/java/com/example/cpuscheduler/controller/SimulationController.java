package com.example.cpuscheduler.controller;

import com.example.cpuscheduler.model.*;
import com.example.cpuscheduler.model.Process;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SimulationController implements Initializable {

    @FXML
    public Label priorityLabel;

    @FXML
    private Pane pane;
    @FXML
    private Label quantumTimeLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField quantumTimeTextField;

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
    private Button goBackButton;

    @FXML
    private Button deleteAllButton;

    @FXML
    private Button calculateButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label averageWaitingTimeLabel;

    @FXML
    private Label turnaroundTimeLabel;

    @FXML
    private Label cpuUtilizationLabel;

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

        if(MainMenuController.currentStage.equals("FCFS")){
            quantumTimeLabel.setVisible(false);
            quantumTimeTextField.setVisible(false);
            priorityColumn.setVisible(false);
            priorityTextField.setVisible(false);
            priorityLabel.setVisible(false);
            idColumn.setPrefWidth(idColumn.getWidth() + 24);
            burstTimeColumn.setPrefWidth(burstTimeColumn.getWidth() + 50);
            arrivalTimeColumn.setPrefWidth(arrivalTimeColumn.getWidth() + 50);
            titleLabel.setText("""
                FCFS(First Come First Serve)
                """);
        }

        if(MainMenuController.currentStage.equals("SJN")){
            quantumTimeLabel.setVisible(false);
            quantumTimeTextField.setVisible(false);
            titleLabel.setText("""
                SJN(Short Job Next)
                """);
        }

        if(MainMenuController.currentStage.equals("Round Robin")) {
            priorityColumn.setVisible(false);
            priorityTextField.setVisible(false);
            priorityLabel.setVisible(false);
            idColumn.setPrefWidth(idColumn.getWidth() + 24);
            burstTimeColumn.setPrefWidth(burstTimeColumn.getWidth() + 50);
            arrivalTimeColumn.setPrefWidth(arrivalTimeColumn.getWidth() + 50);
            titleLabel.setText("""
                    RR(Round Robin)
                    """);
        }
    }

    private void visualizationGanttChart(Scheduler scheduler){
        List<Pair<Integer, Pair<Double,Double>>> processes = scheduler.schedule();


        List<Double> timestamps = new ArrayList<Double>();
        List<Integer> currentProcess = new ArrayList<Integer>();
        List<Double> se1 = new ArrayList<Double>();
        List<Double> se2 = new ArrayList<Double>();
        List<Pair<Double, Pair<Double, Double>>> color = new ArrayList<Pair<Double, Pair<Double, Double>>>();

        for(int i = 0; i < processes.size(); i++){
            Double colorRed = Math.random();
            Double colorGreen = Math.random();
            Double colorYellow = Math.random();
            color.add(new Pair<>(colorRed, new Pair<>(colorGreen, colorYellow)));
        }

        for (Pair<Integer, Pair<Double, Double>> process : processes) {
            timestamps.add(process.getValue().getValue() - process.getValue().getKey());
            currentProcess.add(process.getKey());
            se1.add(process.getValue().getKey());
            se2.add(process.getValue().getValue());
        }


        SequentialTransition sequence = new SequentialTransition();
        int index = 0;
        double time = 0, cnt = 0;
        for (double timestamp : timestamps) {
            double waitTime;
            if(index == 0)waitTime = 0;
            else waitTime = timestamps.get(index - 1);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5 * waitTime));
            double x, y;
            if((time + timestamp) * 60 > pane.getWidth()) {
                time = 0;
                cnt++;
            }
            x = time * 60;
            y = cnt * 80;
            time += timestamp;
            String stringSecond1 = String.valueOf(se1.get(index));
            String stringSecond2 = String.valueOf(se2.get(index));
            String curProcess = String.valueOf(currentProcess.get(index));
            Pair<Double, Pair<Double, Double>> currentColor = color.get(currentProcess.get(index) - 1);
            pause.setOnFinished(event -> {
                Rectangle square = createSquare(currentColor);

                square.setX(x);
                square.setY(y);

                pane.getChildren().add(square);

                Text text = new Text("P" + curProcess);
                text.setFont(new Font("Arial",20));
                text.setFill(Color.BLACK);

                // Đặt chữ vào giữa hình chữ nhật
                text.setX(square.getX() + 60 * timestamp / 2 - text.getLayoutBounds().getWidth() / 2); // Căn giữa theo chiều ngang
                text.setY(square.getY() + 50 / 2 + text.getLayoutBounds().getHeight() / 4); // Căn giữa theo chiều dọc

                Text second1 = new Text(stringSecond1);
                second1.setFont(new Font("Times New Roman",12));
                second1.setFill(Color.RED);

                second1.setX(square.getX());
                second1.setY(square.getY());

                Text second2 = new Text(stringSecond2);
                second2.setFont(new Font("Times New Roman",12));
                second2.setFill(Color.RED);

                second2.setX(square.getX() + 60 * timestamp - text.getLayoutBounds().getWidth());
                second2.setY(square.getY());

                pane.getChildren().addAll(text, second1, second2);

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(square.widthProperty(), 0)), // Bắt đầu từ 0
                        new KeyFrame(Duration.seconds(0.5 * timestamp), new KeyValue(square.widthProperty(), 60 * timestamp)) // Kết thúc tại width
                );
                timeline.setCycleCount(1); // Chỉ chạy 1 lần
                timeline.play();
            });

            sequence.getChildren().add(pause);
            index++;
        }

        sequence.play();
    }

    private Rectangle createSquare(Pair<Double, Pair<Double, Double>> currentColor) {
        Rectangle square = new Rectangle(0, 0, 0, 50);
        square.setFill(Color.color(currentColor.getKey(), currentColor.getValue().getKey(), currentColor.getValue().getValue()));
        return square;
    }

    public void handleAdd(ActionEvent actionEvent) {
        Process newProcess = new Process();
        newProcess.setArrivalTime(Double.parseDouble(arrivalTimeTextField.getText()));
        newProcess.setBurstTime(Double.parseDouble(burstTimeTextField.getText()));
        if(MainMenuController.currentStage.equals("SJN"))newProcess.setPriority(Integer.parseInt(priorityTextField.getText()));
        else newProcess.setPriority(0);
        if(processList.isEmpty())newProcess.setId(1);
        else{
            newProcess.setId(processList.getLast().getId() + 1);
        }
        processList.add(newProcess);
    }

    public void handleDelete(ActionEvent actionEvent) {
        List <Process> selectedProcess = table.getSelectionModel().getSelectedItems();
        int curId = 0;
        for(Process process : processList) {
            if(selectedProcess.contains(process)) {
                curId = process.getId();
                processList.remove(process);
                break;
            }
        }
        for(Process process : processList) {
            if(process.getId() > curId) {
                process.setId(process.getId() - 1);
            }
        }
    }

    public void handleGoBack(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Thoát " + MainMenuController.currentStage + " simulation?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Exit simulation Confirmation");
        alert.setHeaderText(null);

        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cpuscheduler/MainMenuView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            MainMenuController controller = fxmlLoader.getController();
            controller.closeStage(stage);
            stage.setResizable(false);
            stage.setTitle("CPUScheduler");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void handleDeleteAll() {
        processList.clear();
    }

    public void handleCalculate(ActionEvent event) {
        Scheduler schedule;
        if(MainMenuController.currentStage.equals("FCFS")) {
            schedule = new FCFS(processList);
        }else if(MainMenuController.currentStage.equals("SJN")){
            schedule = new SJN(processList);
        }else{
            double quantumTime = Double.parseDouble(quantumTimeTextField.getText());
            schedule = new RoundRobin(processList, quantumTime);
        }
        visualizationGanttChart(schedule);
        averageWaitingTimeLabel.setText("Average Waiting Time: " + schedule.calWaitingTime());
        turnaroundTimeLabel.setText("Turnaround Time: " + schedule.calTurnAroundTime());
        cpuUtilizationLabel.setText("CPU Utilization: " + schedule.calCPUUtilization());
    }

    public void handleReset(){
        pane.getChildren().clear();
        cpuUtilizationLabel.setText("CPU Utilization: ");
        averageWaitingTimeLabel.setText("Average Waiting Time: ");
        turnaroundTimeLabel.setText("Turnaround Time: ");
    }

    public void handleHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Giải thích thuật toán");
        alert.setHeaderText(null);
        alert.setResizable(false);
        alert.getDialogPane().setPrefSize(500, 200);
        if(MainMenuController.currentStage.equals("FCFS")) {
            alert.setContentText("""
                FCFS(First Come First Serve): thực hiện các tiến trình theo thời gian xuất hiện trong CPU, tức là
                tiến trình nào có arrival time nhỏ nhất sẽ được thực hiện trước.
                """);
        }else if(MainMenuController.currentStage.equals("SJN")){
            alert.setContentText("""
                SJN(Short Job Next): mỗi khi có 1 tiến trình mới xuất hiện trong hệ thống, tiến trình hiện tại được
                đưa lại vào ready queue, sau đó tiến trình có burst time nhỏ nhất sẽ được lựa chọn để thực thi, khi
                có nhiều tiến trình có cùng burst time nhỏ nhất, tiến trình có độ ưu tiên cao hơn được lựa chọn.
                """);
        }else{
            alert.setContentText("""
                RR(Round Robin): mỗi tiến trình được cấp một khoảng thời gian cố định quantum time, các tiến trình
                xuất hiện trong hệ thống được xếp vào cuối hàng đợi, khi 1 tiến trình hết quantum time mà chưa
                hoàn thành, nó được xếp vào cuối hàng đợi.
                """);
        }
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.YES){
                System.out.println("yes");
            }else{
                System.out.println("no");
            }
        });
    }
}

