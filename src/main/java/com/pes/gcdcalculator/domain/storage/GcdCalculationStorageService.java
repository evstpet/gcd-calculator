package com.pes.gcdcalculator.domain.storage;

import com.pes.gcdcalculator.domain.vo.Calculation;

import java.util.List;

public interface GcdCalculationStorageService {

    void save(Calculation calculation);

    void remove(Calculation calculation);

    List<Calculation> findCalculations();
}