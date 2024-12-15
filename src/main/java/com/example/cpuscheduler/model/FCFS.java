package com.example.cpuscheduler.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FCFS extends Scheduler {

    public FCFS(List<Process> processes){
        super(processes);
    }

    @Override
    public List<Pair<Integer, Pair<Double, Double>>> schedule(){
        List<Pair<Integer, Pair <Double,Double>>> list = new ArrayList<Pair<Integer, Pair<Double, Double>>>();
        list.add(new Pair<>(1, new Pair<>(0.0, 1.0)));
        list.add(new Pair<>(2, new Pair<>(2.0, 3.0)));
        list.add(new Pair<>(3, new Pair<>(4.0, 5.0)));
        list.add(new Pair<>(1, new Pair<>(6.0, 7.0)));
        list.add(new Pair<>(4, new Pair<>(8.0, 9.0)));
        list.add(new Pair<>(3, new Pair<>(10.0, 11.0)));
        return list;
    }
}
