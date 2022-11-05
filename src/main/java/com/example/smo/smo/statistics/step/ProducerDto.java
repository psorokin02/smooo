package com.example.smo.smo.statistics.step;

public class ProducerDto {
    public Long id;
    public Double nextTaskTime;

    public ProducerDto(Long id, Double nextTaskTime) {
        this.id = id;
        this.nextTaskTime = nextTaskTime;
    }
}
