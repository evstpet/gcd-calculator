package com.pes.gcdcalculator.application.event;

import com.pes.gcdcalculator.application.service.GcdService;
import com.pes.gcdcalculator.domain.storage.GcdCalculationStorageService;
import com.pes.gcdcalculator.domain.vo.Calculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventsRerunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsRerunner.class);

    private GcdCalculationStorageService storageService;

    private GcdService gcdService;

    @Autowired
    public EventsRerunner(GcdCalculationStorageService storageService, GcdService gcdService) {
        this.storageService = storageService;
        this.gcdService = gcdService;
    }

    @Scheduled(initialDelay = 1000)
    public void rerunEvents() {
        List<Calculation> calculations = storageService.findCalculations();
        LOGGER.info("Events to rerun: " + calculations.size());
        for (Calculation calculation : calculations) {
            gcdService.calculateGcd(calculation);
        }
    }

}
