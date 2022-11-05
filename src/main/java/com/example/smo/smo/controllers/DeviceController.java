package com.example.smo.smo.controllers;

import com.example.smo.smo.domain.device.Device;
import com.example.smo.smo.domain.queue.Buffer;

public class DeviceController {

    public final Buffer buffer;

    public DeviceController(Buffer buffer) {
        this.buffer = buffer;
    }

    public void processDeviceFree(Device device) {
        device.makeFree();
        var processedTask = device.previousTask;
        //пытается найти по такому же типу прибора, иначе возвращает первую приоритетную таску очереди
        var nextTask = buffer.getNextTask(processedTask.producerId);
        if(nextTask != null){
            device.handleTask(nextTask, processedTask.endHandlingTime);
        }
        //иначе остается свободным
    }

}
