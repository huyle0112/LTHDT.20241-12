package com.example.cpuscheduler.model;
import java.util.LinkedHashMap;
import java.util.List;

public class FCFSScheduler extends Scheduler {

    // Constructor 
    public FCFSScheduler(List<Process> processes) {
        super(processes);
    }

    // Phương thức lập lịch tiến trình theo thuật toán FCFS
    @Override
    public LinkedHashMap<Integer, Double> schedule() {
        LinkedHashMap<Integer, Double> ganttChart = new LinkedHashMap<>(); // Biểu đồ Gantt
        double currentTime = 0; // Thời gian hiện tại của CPU

        // Sắp xếp các tiến trình theo thời gian đến (arrivalTime)
        processes.sort((p1, p2) -> Double.compare(p1.getArrivalTime(), p2.getArrivalTime()));

        for (Process process : processes) {
            // Nếu thời gian hiện tại nhỏ hơn thời gian đến, CPU sẽ chờ idle
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }

            // Thực hiện xử lý tiến trình
            currentTime += process.getBurstTime();
            ganttChart.put(process.getId(), currentTime); // Ghi lại thời gian hoàn thành vào biểu đồ Gantt

            // Cập nhật thời gian hoàn thành của tiến trình
            process.setCompletionTime(currentTime);

            // Tính thời gian quay vòng (Turnaround Time) và thời gian chờ (Waiting Time)
            process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());
            process.setWaitingTime(process.getTurnaroundTime() - process.getOriginalBurstTime());
         // Thêm tiến trình vào Gantt Chart với thời điểm hoàn thành
            ganttChart.put(process.getId(), process.getCompletionTime());
        }

        return ganttChart; // Trả về biểu đồ Gantt
    }

    // Tính toán thời gian quay vòng trung bình
    @Override
    public double calTurnAroundTime() {
        double totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += process.getTurnaroundTime();
        }
        return totalTurnaroundTime / processes.size();
    }

    // Tính toán thời gian chờ trung bình
    @Override
    public double calWaitingTime() {
        double totalWaitingTime = 0;
        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
        }
        return totalWaitingTime / processes.size();
    }

    // Tính toán hiệu suất sử dụng CPU
    @Override
    public double calCPUUtilizationBased() {
        double totalBurstTime = 0;
        for (Process process : processes) {
            totalBurstTime += process.getOriginalBurstTime();  // Tính tổng thời gian burst ban đầu
        }

        // Tổng thời gian hệ thống đã thực thi (thời gian hoàn thành của tiến trình cuối cùng)
        double maxCompletionTime = Double.MIN_VALUE;

        for (Process process : processes) {
            totalBurstTime += process.getOriginalBurstTime();
            maxCompletionTime = Math.max(maxCompletionTime, process.getCompletionTime());
        }

        return (totalBurstTime / maxCompletionTime) * 100; // Hiệu suất sử dụng CPU
    }
}

