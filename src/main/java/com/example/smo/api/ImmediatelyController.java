package com.example.smo.api;

import com.example.smo.smo.controllers.SmoController;
import com.example.smo.smo.controllers.dto.SmoConfigurationDto;
import com.example.smo.smo.statistics.total.TotalStatisticsDto;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/total")
public class ImmediatelyController {

    @PostMapping
    public TotalStatisticsDto getResultStatistics(
            @RequestBody SmoConfigurationDto dto
    ) {
        Long initialCountTasks = 100L;
        TotalStatisticsDto prevStatistic = calcStatForCount(dto, initialCountTasks);
        TotalStatisticsDto currentStatistic;

        Long oldN = initialCountTasks;
        Long newN = 0L;

        while(true) {
            double p = prevStatistic.probabilityRejectForAllTasks;
            newN = (long)((1.643)*(1.643)*(1 - p)/(p * 0.1 * 0.1));

            if(newN > 25000) {
                prevStatistic = calcStatForCount(dto, 25000L);
                break;
            }
            currentStatistic = calcStatForCount(dto, newN);
            double p2 = currentStatistic.probabilityRejectForAllTasks;
            if(Math.abs(p2-p) < 0.1 * p)
                break;
            prevStatistic = currentStatistic;
            oldN = newN;
        }

        return prevStatistic;
    }

    private TotalStatisticsDto calcStatForCount(
            SmoConfigurationDto dto,
            Long countTasks
    ) {
        dto.totalCountTasks = countTasks;
        var smo = new SmoController(dto);
        while(smo.makeNextStep()){}
        return smo.getTotalStatistic();
    }
}
