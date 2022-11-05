package com.example.smo.smo.controllers;

import com.example.smo.smo.domain.device.Device;
import com.example.smo.smo.domain.producer.Producer;
import com.example.smo.smo.domain.queue.Buffer;
import com.example.smo.smo.statistics.step.StepStatisticDto;
import com.example.smo.smo.statistics.total.TotalStatisticsDto;

import java.util.List;

public class StatisticController {

    public final List<Device> devices;
    public final List<Producer> producers;

    public final Buffer buffer;

    public StatisticController(List<Device> devices, List<Producer> producers, Buffer buffer) {
        this.devices = devices;
        this.producers = producers;
        this.buffer = buffer;
    }

    public StepStatisticDto getStatistic() {
        var statistics = new StepStatisticDto();
        devices.forEach(it -> statistics.devices.add(it.getStepStatistic()));
        producers.forEach(it -> statistics.producers.add(it.getStepStatisticDto()));
        buffer.queue.forEach(it -> statistics.queue.add(it.toDto()));
        return statistics;
    }

    public TotalStatisticsDto getTotalStatistic() {
        var statistic = new TotalStatisticsDto();

        Double totalSystemTime = 0d;
        Long totalTasksCount = 0L;
        Long rejectedTasksCount = 0L;
        Double totalTasksTimeInSystem = 0d;
        for (var producer : producers)
            for (var task : producer.producedTasks) {
                if (task.endHandlingTime > totalSystemTime) totalSystemTime = task.endHandlingTime;
                totalTasksCount++;
                if (task.isRejected) rejectedTasksCount++;
                totalTasksTimeInSystem += task.getTimeInSystem();
            }

        statistic.totalTasksCount = totalTasksCount;
        statistic.totalTime = totalSystemTime;
        statistic.probabilityRejectForAllTasks = (double) rejectedTasksCount / (double) totalTasksCount;
        statistic.averageTaskTimeInSystem = totalTasksTimeInSystem / totalTasksCount;

        for(var device: devices)
            statistic.devices.add(device.getTotalStatistic(totalSystemTime));

        for(var producer: producers)
            statistic.producers.add(producer.getTotalStatistic());

        var summaryDeviceBusy = 0d;
        for(var device: statistic.devices) summaryDeviceBusy += device.coefficientOfBusy;
        statistic.averageDeviceBusy = summaryDeviceBusy / devices.size();

        return statistic;
    }

}
