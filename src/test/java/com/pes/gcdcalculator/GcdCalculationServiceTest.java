package com.pes.gcdcalculator;

import com.pes.gcdcalculator.domain.service.GcdCalculationService;
import com.pes.gcdcalculator.domain.service.GcdCalculationServiceImpl;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GcdCalculationServiceTest {

    private GcdCalculationService gcdCalculationService = new GcdCalculationServiceImpl();

    @Test(dataProvider = "testData")
    public void testCalculate(long first, long second, long expectedResult) {
        long result = gcdCalculationService.calculate(first, second);

        assertThat(result).isEqualTo(expectedResult);
    }

    @DataProvider(name = "testData")
    public static Object[][] primeNumbers() {
        return new Object[][]{
                {11, 33, 11},
                {-11, 33, 11},
                {11, -33, 11},
                {0, 2, 2},
                {-2, -2, 2}
        };
    }
}
