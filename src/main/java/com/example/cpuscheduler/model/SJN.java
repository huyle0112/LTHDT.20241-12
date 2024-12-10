package com.example.cpuscheduler.model;

import java.util.List;

public class SJN extends Scheduler {
    public SJN(List<com.example.cpuscheduler.model.Process> processes) {
        super(processes);
    }

    @Override
    public List<Process> schedule() {
        return null;
    };
}
