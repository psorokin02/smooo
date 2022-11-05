package com.example.smo.smo.controllers;

import com.example.smo.smo.controllers.dto.SmoConfigurationDto;
import com.example.smo.smo.domain.device.Device;
import com.example.smo.smo.domain.producer.Producer;
import com.example.smo.smo.domain.queue.Buffer;
import com.example.smo.smo.statistics.step.StepStatisticDto;
import com.example.smo.smo.statistics.total.TotalStatisticsDto;

import java.lang.module.Configuration;
import java.util.ArrayList;
import java.util.List;

public class SmoController {

    public List<Device> devices;
    public List<Producer> producers;

    public Buffer buffer;


    public final ProducerController producerController;
    public final DeviceController deviceController;
    public final StatisticController statisticController;


    public SmoController(SmoConfigurationDto configuration) {
        initDevices(configuration);
        initProducers(configuration.countProducers, configuration.producerIntensity);
        buffer = new Buffer(configuration.bufferSize);

        producerController = new ProducerController(buffer, devices, configuration.totalCountTasks, producers);
        deviceController = new DeviceController(buffer);
        statisticController = new StatisticController(devices, producers, buffer);
    }

    //true - шаг сделан, false - шаги закончились
    public boolean makeNextStep() {
        var minimalTimeDevice = getDeviceWithMinimalTime();
        var minimalTimeProducer = getProducerWithMinimalTime();

        if (minimalTimeDevice == null && minimalTimeProducer == null)
            return false;

        if (minimalTimeDevice == null){
            producerController.processProducerStep(minimalTimeProducer);
        }
        else if (minimalTimeProducer == null){
            deviceController.processDeviceFree(minimalTimeDevice);
        }
        else {
            if (minimalTimeDevice.nextFreeTime.compareTo(minimalTimeProducer.nextTaskTime) < 0)
                deviceController.processDeviceFree(minimalTimeDevice);
            else
                producerController.processProducerStep(minimalTimeProducer);
        }
        return true;
    }

    public StepStatisticDto getStepStatistics() {
        return statisticController.getStatistic();
    }

    public TotalStatisticsDto getTotalStatistic() {
        return statisticController.getTotalStatistic();
    }

    private Device getDeviceWithMinimalTime() {
        Device minDevice = null;
        for (var device : devices) {
            if (!device.isBusy())
                continue;
            if (minDevice == null || minDevice.nextFreeTime.compareTo(device.nextFreeTime) > 0)
                minDevice = device;
        }
        return minDevice;
    }

    private Producer getProducerWithMinimalTime() {
        Producer minProducer = null;
        for (var producer : producers) {
            if (producer.isStopped())
                continue;
            if (minProducer == null || minProducer.nextTaskTime > producer.nextTaskTime)
                minProducer = producer;
        }
        return minProducer;
    }

    private void initProducers(Long count, Double intensity) {
        this.producers = new ArrayList<>();
        for (int i = 1; i <= count; ++i) {
            producers.add(new Producer((long) i, intensity));
        }
    }

    private void initDevices(SmoConfigurationDto configuration) {
        this.devices = new ArrayList<>();
        for (int i = 1; i <= configuration.countDevices; ++i) {
            devices.add(new Device((long) i, configuration.taskProcessDeviceTimeLowerBound, configuration.taskProcessDeviceTimeUpperBound));
        }
    }
}
