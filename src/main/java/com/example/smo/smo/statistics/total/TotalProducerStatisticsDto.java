package com.example.smo.smo.statistics.total;

public class TotalProducerStatisticsDto {
    public Long id;
    public Long generatedTasksCount;
    public Double probabilityRejectTask;
    public Double averageTaskTimeInSystem;
    public Double averageTaskTimeHandling;
    public Double averageTaskTimeInBuffer;
    public Double dispersionAverageTaskTimeHandling;
    public Double dispersionAverageTaskTimeInBuffer;

    public TotalProducerStatisticsDto() {
    }
}
