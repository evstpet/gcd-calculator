package com.pes.gcdcalculator.application.service;

import com.pes.gcdcalculator.application.event.EventSender;
import com.pes.gcdcalculator.domain.service.GcdCalculationService;
import com.pes.gcdcalculator.domain.storage.GcdCalculationStorageService;
import com.pes.gcdcalculator.domain.vo.Calculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static java.lang.Math.abs;


@Service
public class GcdService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcdService.class);

    private GcdCalculationStorageService storageService;

    private GcdCalculationService calculationService;

    private EventSender eventSender;

    @Autowired
    public GcdService(
            GcdCalculationStorageService storageService,
            GcdCalculationService calculationService,
            EventSender eventSender
    ) {
        this.storageService = storageService;
        this.calculationService = calculationService;
        this.eventSender = eventSender;
    }

    @Transactional
    public void calculateGcd(Calculation calculation) {
        try {
            long result = calculationService.calculate(
                    abs(calculation.getFirst()),
                    abs(calculation.getSecond())
            );
            calculation.setResult(result);
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
            calculation.setError(ex.toString());
        }

        storageService.remove(calculation);
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        eventSender.sendEvent(calculation);
                    }
                }
        );
    }

}
