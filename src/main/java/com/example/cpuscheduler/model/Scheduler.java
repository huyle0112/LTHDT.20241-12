package com.example.cpuscheduler.model;

import java.util.List;
import java.util.LinkedHashMap;

public abstract class Scheduler {
    protected List<Process> processes;  // Danh sách các tiến trình

    // Constructor để khởi tạo danh sách tiến trình
    public Scheduler(List<Process> processes) {
        this.processes = processes;
    }

    // Các phương thức trừu tượng để tính toán các chỉ số hiệu suất
    public abstract double calTurnAroundTime();
    public abstract double calWaitingTime();
    public abstract double calCPUUtilizationBased();

    // Phương thức lập lịch các tiến trình (các lớp con sẽ cài đặt)
    public abstract LinkedHashMap<Integer, Double> schedule();

	public double calculateCPUUtilization() {
		// TODO Auto-generated method stub
		return 0;
	}
}


