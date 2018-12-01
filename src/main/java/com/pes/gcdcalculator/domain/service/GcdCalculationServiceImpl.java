package com.pes.gcdcalculator.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@Slf4j
public class GcdCalculationServiceImpl implements GcdCalculationService {

    @Override
    public long calculate(long first, long second) {
        log.info("first = " + first + ", second = " + second);

        long result = BigInteger.valueOf(first)
                .gcd(BigInteger.valueOf(second))
                .longValue();

        log.info("result = " + result);
        return result;
    }
}
