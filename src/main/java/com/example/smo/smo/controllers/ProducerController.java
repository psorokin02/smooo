package com.example.smo.smo.controllers;

import com.example.smo.smo.domain.device.Device;
import com.example.smo.smo.domain.producer.Producer;
import com.example.smo.smo.domain.queue.Buffer;

import java.util.List;

public class ProducerController {

    public final Buffer buffer;
    public final List<Device> devices;

    public final List<Producer> producers;

    public final Long totalNeededTasksCount;

    public Long generatedTasksCount;

    public ProducerController(Buffer buffer, List<Device> devices, Long totalNeededTasksCount, List<Producer> producers) {
        this.buffer = buffer;
        this.devices = devices;
        this.producers = producers;
        this.totalNeededTasksCount = totalNeededTasksCount;
        this.generatedTasksCount = 0L;
    }

    public void processProducerStep(Producer producer) {

        if(generatedTasksCount.equals(totalNeededTasksCount)) {
            producer.stopProduce();
            return;
        }

        var task = producer.getNextTask();

        var freeDevice = getFreeDevice();
        if(freeDevice == null) {
            buffer.add(task);
            increaseCountTasks();
            return;
        }
        freeDevice.handleTask(task, task.createdTime);
        increaseCountTasks();
    }

    public Device getFreeDevice() {
        for(var device: devices) {
            if(device.nextFreeTime.equals(-1d))
                return device;
        }
        return null;
    }

    private void increaseCountTasks() {
        generatedTasksCount++;
        if(generatedTasksCount.equals(totalNeededTasksCount)){
            producers.forEach(Producer::stopProduce);
        }
    }
}
