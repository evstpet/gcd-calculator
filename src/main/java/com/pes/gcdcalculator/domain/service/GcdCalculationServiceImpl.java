package com.pes.gcdcalculator.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.lang.Math.abs;

@Service
public class GcdCalculationServiceImpl implements GcdCalculationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcdCalculationService.class);

    @Override
    public long calculate(long first, long second) {
        LOGGER.info("first = " + first + ", second = " + second);

        first = abs(first);
        second = abs(second);

        while (first != 0 && second != 0) {
            if (first > second) {
                first = first % second;
            } else {
                second = second % first;
            }
        }
        long result = first + second;

        LOGGER.info("result = " + result);
        return result;
    }
}
