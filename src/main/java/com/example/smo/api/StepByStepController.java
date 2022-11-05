package com.example.smo.api;

import com.example.smo.smo.controllers.SmoController;
import com.example.smo.smo.controllers.dto.SmoConfigurationDto;
import com.example.smo.smo.statistics.step.StepStatisticDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/step")
@CrossOrigin
public class StepByStepController {

    public SmoController smo = null;

    @PostMapping("/init")
    public StepStatisticDto init(@RequestBody SmoConfigurationDto dto) {
        smo = new SmoController(dto);
        return smo.getStepStatistics();
    }

    @PostMapping("/make-step")
    public StepStatisticDto nextStep() {
        if(smo == null){
            throw new RuntimeException("Cannot make step - smo not set up");
        }
        var isMadeStep = smo.makeNextStep();
        var dto = smo.getStepStatistics();
        dto.isEnded = !isMadeStep;
        return dto;
    }

}
