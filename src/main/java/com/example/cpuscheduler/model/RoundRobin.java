package com.example.cpuscheduler.model;

import java.util.List;

public class RoundRobin extends Scheduler {
    private double quantumTime;

    public double getQuantumTime() {
        return quantumTime;
    }

    public void setQuantumTime(double quantumTime) {
        this.quantumTime = quantumTime;
    }

    public RoundRobin(List<com.example.cpuscheduler.model.Process> processes) {
        super(processes);
    }

    @Override
    public List<Process> schedule() {
        return null;
    }

}
