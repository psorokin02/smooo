package com.example.smo.smo.statistics.step;

import java.util.ArrayList;
import java.util.List;

public class StepStatisticDto {

    public List<ProducerDto> producers;
    public List<DeviceDto> devices;
    public List<TaskDto> queue;

    public boolean isEnded = false;

    public StepStatisticDto() {
        producers = new ArrayList<>();
        devices = new ArrayList<>();
        queue = new ArrayList<>();
    }
}
