package com.example.smo.smo.domain.producer;

import com.example.smo.smo.domain.Task;
import com.example.smo.smo.statistics.step.ProducerDto;
import com.example.smo.smo.statistics.total.TotalProducerStatisticsDto;

import java.util.ArrayList;
import java.util.List;

public class Producer {
    public Long id;
    public Double intensity;

    public Double nextTaskTime;
    public List<Task> producedTasks;

    public Producer(Long id, Double intensity) {
        this.id = id;
        this.intensity = intensity;
        this.nextTaskTime = 0d;
        this.producedTasks = new ArrayList<>();
        recalculateNextTime();
    }

    public Task getNextTask() {
        if(nextTaskTime == -1d)
            throw new RuntimeException("Trying to get new task prducerId: " + id + " but producer was stopped");
        var task = new Task();
        task.producerId = id;
        task.createdTime = nextTaskTime;
        producedTasks.add(task);
        recalculateNextTime();
        return task;
    }

    public void stopProduce() {
        nextTaskTime = -1d;
    }

    private void recalculateNextTime() {
        var delta = -1/intensity * Math.log(Math.random())/Math.log(Math.E);
        nextTaskTime += delta;
    }

    public boolean isStopped() {
        return nextTaskTime.equals(-1d);
    }

    public ProducerDto getStepStatisticDto() {
        return new ProducerDto(id, nextTaskTime);
    }

    public TotalProducerStatisticsDto getTotalStatistic() {
        var statistic = new TotalProducerStatisticsDto();

        statistic.generatedTasksCount = (long) producedTasks.size();

        var countRejectedTasks = 0;
        for(var task: producedTasks) if(task.isRejected) countRejectedTasks++;
        statistic.probabilityRejectTask = (double) countRejectedTasks / (double) statistic.generatedTasksCount;

        var summaryTimeTasksInSystem = 0d;
        for(var task: producedTasks)
            summaryTimeTasksInSystem += task.getTimeInSystem();
        statistic.averageTaskTimeInSystem = summaryTimeTasksInSystem / (double) statistic.generatedTasksCount;

        var summaryTimeHandling = 0d;
        for(var task: producedTasks)
            summaryTimeHandling += task.getHandlingTime();
        statistic.averageTaskTimeHandling = summaryTimeHandling / (double) statistic.generatedTasksCount;

        var summaryTimeTasksInBuffer = 0d;
        for(var task: producedTasks)
            summaryTimeTasksInBuffer += task.getTimeInBuffer();
        statistic.averageTaskTimeInBuffer = summaryTimeTasksInBuffer / (double) statistic.generatedTasksCount;

        var sumOfSubtractionSquares1 = 0d;
        for(var task: producedTasks)
            sumOfSubtractionSquares1 +=
                    (task.getHandlingTime() - statistic.averageTaskTimeHandling) *
                            (task.getHandlingTime() - statistic.averageTaskTimeHandling);

        statistic.dispersionAverageTaskTimeHandling = sumOfSubtractionSquares1 / (double) statistic.generatedTasksCount;

        var sumOfSubtractionSquares2 = 0d;
        for(var task: producedTasks)
            sumOfSubtractionSquares2 +=
                    (task.getTimeInBuffer() - statistic.averageTaskTimeInBuffer) *
                            (task.getTimeInBuffer() - statistic.averageTaskTimeInBuffer);

        statistic.dispersionAverageTaskTimeInBuffer = sumOfSubtractionSquares2 / (double) statistic.generatedTasksCount;

        statistic.id = this.id;
        return statistic;
    }
}
