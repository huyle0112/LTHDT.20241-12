import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class SimulationController {

    @FXML
    private TableView<Process> tableProcesses;
    @FXML
    private TableColumn<Process, Integer> idColumn;
    @FXML
    private TableColumn<Process, Double> arrivalTimeColumn;
    @FXML
    private TableColumn<Process, Double> burstTimeColumn;
    @FXML
    private TableColumn<Process, Integer> priorityColumn;

    @FXML
    private TextField textFieldArrivalTime;
    @FXML
    private TextField textFieldBurstTime;
    @FXML
    private TextField textFieldPriority;

    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonCalculate;
    @FXML
    private Button buttonVisualization;
    @FXML
    private Label labelMetrics;

    private ObservableList<Process> processList = FXCollections.observableArrayList();

    public void initialize() {
        // Khởi tạo cột
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        burstTimeColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

        // Liên kết table view với observable list
        tableProcesses.setItems(processList);

        // Set button actions
        buttonAdd.setOnAction(e -> add());
        buttonDelete.setOnAction(e -> delete());
        buttonCalculate.setOnAction(e -> calculate());
        buttonVisualization.setOnAction(e -> handleChart());
    }

    public void add() {
        try {
            double arrivalTime = Double.parseDouble(textFieldArrivalTime.getText());
            double burstTime = Double.parseDouble(textFieldBurstTime.getText());
            int priority = Integer.parseInt(textFieldPriority.getText());

            Process newProcess = new Process(processList.size() + 1, arrivalTime, burstTime, priority);
            processList.add(newProcess);

            // Clear trường input
            textFieldArrivalTime.clear();
            textFieldBurstTime.clear();
            textFieldPriority.clear();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input. Please enter valid numbers.");
            alert.showAndWait();
        }
    }

    public void delete() {
        Process selectedProcess = tableProcesses.getSelectionModel().getSelectedItem();
        if (selectedProcess != null) {
            processList.remove(selectedProcess);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No process selected for deletion.");
            alert.showAndWait();
        }
    }

    public void calculate() {
        // Thực hiện lập lịch và tính toán
        Scheduler scheduler = new FCFS(processList);  // Thay thế bằng thuật toán mong muốn
        scheduler.schedule();
        labelMetrics.setText("Avg Waiting Time: " + scheduler.calWaitingTime() +
                "\nAvg Turnaround Time: " + scheduler.calTurnaroundTime() +
                "\nCPU Utilization: " + scheduler.calCPUUtilizationBased());
    }

    public void handleChart() {
        // Mở biểu đồ Gantt
        ChartController chartController = new ChartController(processList);
        chartController.initialize();
    }

    public void handleBack() {
        // Trả về menu chính
    }
}
