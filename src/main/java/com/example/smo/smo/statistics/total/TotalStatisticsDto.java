package com.example.smo.smo.statistics.total;

import java.util.ArrayList;
import java.util.List;

public class TotalStatisticsDto {
    public List<TotalDeviceStatisticsDto> devices;
    public List<TotalProducerStatisticsDto> producers;
    public Double totalTime;
    public Double probabilityRejectForAllTasks;
    public Long totalTasksCount;

    public Double averageTaskTimeInSystem;

    public Double averageDeviceBusy;

    public TotalStatisticsDto() {
        devices = new ArrayList<>();
        producers = new ArrayList<>();
    }
}
