package com.example.cpuscheduler.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler {
    private List<com.example.cpuscheduler.model.Process> processes = new ArrayList<com.example.cpuscheduler.model.Process>();

    public Scheduler(List<com.example.cpuscheduler.model.Process> processes) {
        this.processes = processes;
    }

    public double calTurnAroundTime() {
        return 0;
    };

    public double calWaitingTime(){
        return 0;
    };

    public double calCPUUtilizationBased(){
        return 0;
    };

    public List<Process> schedule(){
        return null;
    }
}
