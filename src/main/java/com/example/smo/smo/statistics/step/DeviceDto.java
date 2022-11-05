package com.example.smo.smo.statistics.step;

public class DeviceDto {
    public Long id;
    public TaskDto currentTask;
    public Double nextFreeTime;

    public DeviceDto(Long id, TaskDto currentTask, Double nextFreeTime) {
        this.id = id;
        this.currentTask = currentTask;
        this.nextFreeTime = nextFreeTime;
    }
}
