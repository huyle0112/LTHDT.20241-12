package com.example.cpuscheduler.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SJN extends Scheduler {
    public SJN(List<com.example.cpuscheduler.model.Process> processes) {
        super(processes);
    }

    @Override
    public List<Pair<Integer, Pair<Double, Double>>> schedule(){
        List<Pair<Integer, Pair<Double,Double>>> list = new ArrayList<Pair<Integer, Pair<Double,Double>>>();
        return list;
    }
}
