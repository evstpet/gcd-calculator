package com.pes.gcdcalculator.domain.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@SpringBootTest(classes = {GcdCalculationService.class})
public class CalculationServiceTests extends AbstractTestNGSpringContextTests {

    private GcdCalculationService gcdCalculationService = new GcdCalculationServiceImpl();

    @Test(dataProvider = "testData")
    public void testCalculate(long first, long second, long expectedResult) {
        long result = gcdCalculationService.calculate(first, second);

        assertEquals(result, expectedResult);
    }

    @DataProvider(name = "testData")
    public static Object[][] testData() {
        return new Object[][]{
                {11, 33, 11},
                {-11, 33, 11},
                {11, -33, 11},
                {0, 2, 2},
                {-2, -2, 2}
        };
    }
}
