package com.example.smo.smo.controllers.dto;

public class SmoConfigurationDto {
    public Long countProducers;
    public Long countDevices;
    public Long bufferSize;
    public Double taskProcessDeviceTimeLowerBound;

    public Double taskProcessDeviceTimeUpperBound;
    public Double producerIntensity;

    public Long totalCountTasks;

    public SmoConfigurationDto() {
    }
}
