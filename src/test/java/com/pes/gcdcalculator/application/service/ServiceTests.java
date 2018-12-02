package com.pes.gcdcalculator.application.service;

import com.pes.gcdcalculator.application.event.EventSender;
import com.pes.gcdcalculator.domain.service.GcdCalculationService;
import com.pes.gcdcalculator.domain.vo.Calculation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@SpringBootTest(classes = {GcdService.class})
public class ServiceTests extends AbstractTestNGSpringContextTests {

    @MockBean
    private static GcdCalculationService calculationService = mock(GcdCalculationService.class);

    @MockBean
    private static EventSender eventSender = mock(EventSender.class);

    private GcdService gcdService;

    private static final Long ID = 1L;
    private static final Long FIRST = 11L;
    private static final Long SECOND = 22L;
    private static final Long RESULT = 11L;
    private static final String ERROR = "java.lang.RuntimeException: Error";
    private static final String ERROR_MESSAGE = "Error";

    @BeforeClass
    public void setUpClass() {
        gcdService = new GcdService(calculationService, eventSender);
    }

    @Test(dataProvider = "testData")
    public void testCalculateGcd(
            Runnable preconditions,
            Calculation expectedCalculation
    ) {
        preconditions.run();

        Calculation calculation = createSimpleCalculation(null, null);

        gcdService.calculateGcd(calculation);

        assertEquals(calculation, expectedCalculation);
    }

    @DataProvider(name = "testData")
    public static Object[][] testData() {
        return new Object[][] {
                {
                        (Runnable) () -> when(calculationService.calculate(FIRST, SECOND)).thenReturn(RESULT),
                        createSimpleCalculation(RESULT, null)
                },
                {
                        (Runnable) () -> when(calculationService.calculate(FIRST, SECOND))
                                .thenThrow(new RuntimeException(ERROR_MESSAGE)),
                        createSimpleCalculation(null, ERROR)
                }
        };
    }

    private static Calculation createSimpleCalculation(Long result, String error) {
        return Calculation.builder()
                .id(ID)
                .first(FIRST)
                .second(SECOND)
                .result(result)
                .error(error)
                .build();
    }
}
