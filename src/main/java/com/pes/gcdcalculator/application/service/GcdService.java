package com.pes.gcdcalculator.application.service;

import com.pes.gcdcalculator.application.event.EventSender;
import com.pes.gcdcalculator.domain.service.GcdCalculationService;
import com.pes.gcdcalculator.domain.vo.Calculation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class GcdService {

    private GcdCalculationService calculationService;

    private EventSender eventSender;

    @Autowired
    public GcdService(
            GcdCalculationService calculationService,
            EventSender eventSender
    ) {
        this.calculationService = calculationService;
        this.eventSender = eventSender;
    }

    @Transactional
    public void calculateGcd(Calculation calculation) {
        try {
            long result = calculationService.calculate(calculation.getFirst(), calculation.getSecond());
            calculation.setResult(result);
        } catch (Exception ex) {
            log.error(ex.toString());
            calculation.setError(ex.toString());
        }

        eventSender.sendEvent(calculation);
    }

}
