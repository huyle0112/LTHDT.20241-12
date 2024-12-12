package com.example.cpuscheduler.model;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobin extends Scheduler {
    private double quantumTime;  // Thời gian lượng tử cho mỗi tiến trình

    // Constructor khởi tạo với danh sách tiến trình và thời gian lượng tử
    public RoundRobin(List<Process> processes, double quantumTime) {
        super(processes);  // Gọi constructor của lớp cha (Scheduler)
        this.quantumTime = quantumTime;
    }

    // Phương thức để thiết lập lại thời gian lượng tử
    public void setQuantumTime(double quantumTime) {
        this.quantumTime = quantumTime;
    }

    // Tính toán thời gian quay vòng (Turnaround Time)
    @Override
    public double calTurnAroundTime() {
        double totalTurnaroundTime = 0;
        for (Process process : processes) {
            double turnaroundTime = process.getCompletionTime() - process.getArrivalTime();
            totalTurnaroundTime += turnaroundTime;
        }
        return totalTurnaroundTime / processes.size();  // Trung bình thời gian quay vòng
    }

    // Tính toán thời gian chờ (Waiting Time)
    @Override
    public double calWaitingTime() {
        double totalWaitingTime = 0;
        for (Process process : processes) {
            double waitingTime = process.getTurnaroundTime() - process.getOriginalBurstTime();
            totalWaitingTime += waitingTime;
        }
        return totalWaitingTime / processes.size();  // Trung bình thời gian chờ
    }

    // Tính toán hiệu suất sử dụng CPU
    public double calculateCPUUtilization() {
        double totalBurstTime = 0;
        for (Process process : processes) {
            totalBurstTime += process.getOriginalBurstTime();  // Tính tổng thời gian burst ban đầu
        }

        // Tổng thời gian hệ thống đã thực thi (thời gian hoàn thành của tiến trình cuối cùng)
        double maxCompletionTime = Double.MIN_VALUE;  
        for (Process process : processes) {
            if (process.getCompletionTime() > maxCompletionTime) {
                maxCompletionTime = process.getCompletionTime();  // Cập nhật giá trị lớn nhất
            }
        }
        double totalTime = maxCompletionTime;
        return (totalBurstTime / totalTime) * 100;  // Tính theo tỷ lệ phần trăm
    }

    // Phương thức lập lịch tiến trình theo thuật toán Round Robin
    @Override
    public LinkedHashMap<Integer, Double> schedule() {
        LinkedHashMap<Integer, Double> ganttChart = new LinkedHashMap<>();  // Biểu đồ Gantt
        Queue<Process> queue = new LinkedList<>(processes);  // Hàng đợi các tiến trình
        double currentTime = 0;  // Thời gian hiện tại

        // Xử lý các tiến trình cho đến khi hàng đợi trống
        while (!queue.isEmpty()) {
            Process process = queue.poll();  // Lấy tiến trình đầu tiên trong hàng đợi
            double remainingBurst = process.getBurstTime();  // Lấy thời gian còn lại của tiến trình

            if (remainingBurst <= quantumTime) {
                // Tiến trình hoàn thành trong thời gian lượng tử
                currentTime += remainingBurst;
                ganttChart.put(process.getId(), currentTime);  // Ghi thời gian kết thúc vào biểu đồ Gantt
                process.setCompletionTime(currentTime);  // Cập nhật thời gian hoàn thành
                process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());  // Tính Turnaround Time
                process.setWaitingTime(process.getTurnaroundTime() - process.getOriginalBurstTime());  // Tính Waiting Time
            } else {
                // Tiến trình chưa hoàn thành trong thời gian lượng tử
                currentTime += quantumTime;
                ganttChart.put(process.getId(), currentTime);  // Ghi thời gian kết thúc vào biểu đồ Gantt
                // Tiến trình tiếp tục vào hàng đợi với thời gian còn lại
                process.setBurstTime(remainingBurst - quantumTime);  // Cập nhật burst time còn lại
                queue.offer(process);  // Tiến trình quay lại hàng đợi
            }
        }

        return ganttChart;  // Trả về biểu đồ Gantt
    }

    @Override
    public double calCPUUtilizationBased() {
        // TODO Auto-generated method stub
        return 0;
    }
}
