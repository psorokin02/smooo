package com.example.smo.smo.domain;

import com.example.smo.smo.statistics.step.TaskDto;

public class Task {
    public Long deviceId;
    public Long producerId;
    public Double createdTime;
    public Double startHandlingTime = 0d;
    public Double endHandlingTime = 0d;
    public boolean isRejected;

    public Task() {
    }

    public TaskDto toDto() {
        return new TaskDto(
                deviceId,
                producerId,
                createdTime,
                startHandlingTime,
                endHandlingTime,
                isRejected
        );
    }

    public Double getTimeInBuffer() {
        if(isRejected)
            return 0d;
        return startHandlingTime - createdTime;
    }

    public Double getHandlingTime() {
        if(isRejected)
            return 0d;
        return endHandlingTime - startHandlingTime;
    }

    public Double getTimeInSystem() {
        if(isRejected)
            return 0d;
        return endHandlingTime - createdTime;
    }
}
