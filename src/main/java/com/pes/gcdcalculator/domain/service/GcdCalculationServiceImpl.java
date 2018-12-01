package com.pes.gcdcalculator.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.lang.Math.abs;

@Service
@Slf4j
public class GcdCalculationServiceImpl implements GcdCalculationService {

    @Override
    public long calculate(long first, long second) {
        log.info("first = " + first + ", second = " + second);

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

        log.info("result = " + result);
        return result;
    }
}
