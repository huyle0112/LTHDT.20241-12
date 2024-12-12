package com.example.cpuscheduler.model;
public class Process {
    private int id;               // ID của tiến trình
    private double arrivalTime;   // Thời gian đến của tiến trình
    private double burstTime;     // Thời gian xử lý (burst time) của tiến trình
    private double originalBurstTime; // Thời gian xử lý ban đầu (không thay đổi)
    private int priority;         // Độ ưu tiên của tiến trình
    private double completionTime; // Thời gian hoàn thành của tiến trình
    private double turnaroundTime; // Thời gian quay vòng (Turnaround Time)
    private double waitingTime;    // Thời gian chờ (Waiting Time)

    // Constructor để khởi tạo tiến trình với priority
    public Process(int id, double arrivalTime, double burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.originalBurstTime = burstTime; // Lưu trữ thời gian burst ban đầu
        this.priority = 0; // Gán priority mặc định là 0
        this.completionTime = 0; // Chưa hoàn thành
        this.turnaroundTime = 0; // Chưa tính toán
        this.waitingTime = 0; // Chưa tính toán
    }

    // Các phương thức getter và setter
    public int getId() {
        return id;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public double getOriginalBurstTime() {
        return originalBurstTime; // Trả về burst time ban đầu
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime; // Cập nhật burst time hiện tại
    }

    public int getPriority() {
        return priority;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(double completionTime) {
        this.completionTime = completionTime;
    }

    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(double turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }
}
