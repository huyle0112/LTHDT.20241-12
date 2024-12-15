package com.example.cpuscheduler.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class RoundRobin extends Scheduler {
    private double quantumTime;

    public double getQuantumTime() {
        return quantumTime;
    }

    public void setQuantumTime(double quantumTime) {
        this.quantumTime = quantumTime;
    }

    public RoundRobin(List<com.example.cpuscheduler.model.Process> processes, double quantumTime) {
        super(processes);
        this.quantumTime = quantumTime;
    }

    @Override
    public List<Pair<Integer, Pair<Double, Double>>> schedule(){
        List<Pair<Integer, Pair<Double,Double>>> list = new ArrayList<Pair<Integer, Pair<Double,Double>>>();
        return list;
    }
}
