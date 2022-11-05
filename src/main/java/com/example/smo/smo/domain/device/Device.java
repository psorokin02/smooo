package com.example.smo.smo.domain.device;

import com.example.smo.smo.domain.Task;
import com.example.smo.smo.statistics.step.DeviceDto;
import com.example.smo.smo.statistics.total.TotalDeviceStatisticsDto;

import java.util.ArrayList;
import java.util.List;

public class Device {
    public final Long id;
    public final Double lowerBound;
    public final Double upperBound;
    public Task currentTask;
    public Task previousTask;
    public Double nextFreeTime;

    public final List<Task> handledTasks;

    public Device(Long id, Double lowerBound, Double upperBound) {
        this.id = id;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        currentTask = null;
        previousTask = null;
        nextFreeTime = -1d;
        handledTasks = new ArrayList<>();
    }

    public void handleTask(Task task, Double nowTime) {
        if (currentTask != null || nextFreeTime != -1d) {
            throw new RuntimeException("Device is busy but trying to handle new task");
        }
        task.startHandlingTime = nowTime;
        task.deviceId = id;
        this.nextFreeTime = nowTime + getTaskProcessTime();
        task.endHandlingTime = nextFreeTime;
        this.currentTask = task;
    }

    public void makeFree() {
        nextFreeTime = -1d;
        handledTasks.add(currentTask);
        previousTask = currentTask;
        currentTask = null;
    }

    public boolean isBusy() {
        return !nextFreeTime.equals(-1d);
    }

    public DeviceDto getStepStatistic() {
        return new DeviceDto(id, currentTask == null ? null : currentTask.toDto(), nextFreeTime);
    }

    public TotalDeviceStatisticsDto getTotalStatistic(Double totalSystemTime) {
        double totalBusyTime = 0d;
        for(var task: handledTasks) {
            totalBusyTime += task.endHandlingTime - task.startHandlingTime;
        }
        var dto = new TotalDeviceStatisticsDto();
        dto.coefficientOfBusy = totalBusyTime / totalSystemTime;

        dto.id = this.id;
        return dto;
    }

    private Double getTaskProcessTime() {
        return lowerBound + (upperBound - lowerBound) * Math.random();
    }
}
