package com.example.smo.smo.statistics.step;

public class TaskDto {
    public Long deviceId;
    public Long producerId;
    public Double createdTime;
    public Double startHandlingTime;
    public Double endHandlingTime;
    public boolean isRejected;

    public TaskDto(Long deviceId,
                     Long producerId,
                     Double createdTime,
                     Double startHandlingTime,
                     Double endHandlingTime,
                     boolean isRejected) {
        this.deviceId = deviceId;
        this.producerId = producerId;
        this.createdTime = createdTime;
        this.startHandlingTime = startHandlingTime;
        this.endHandlingTime = endHandlingTime;
        this.isRejected = isRejected;
    }
}
