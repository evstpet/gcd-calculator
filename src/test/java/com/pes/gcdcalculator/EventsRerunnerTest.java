package com.pes.gcdcalculator;

import com.pes.gcdcalculator.application.event.EventsRerunner;
import com.pes.gcdcalculator.application.service.GcdService;
import com.pes.gcdcalculator.domain.storage.GcdCalculationStorageService;
import com.pes.gcdcalculator.domain.vo.Calculation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventsRerunnerTest {

    private GcdCalculationStorageService storageService;

    private GcdService gcdService;

    private EventsRerunner eventsRerunner;

    @BeforeMethod
    public void setUp() {
        storageService = mock(GcdCalculationStorageService.class);
        gcdService = mock(GcdService.class);
        eventsRerunner = new EventsRerunner(storageService, gcdService);

        when(storageService.findCalculations()).thenReturn(twoMockedCalculations());
    }

    @Test
    public void testRerunEvents() {
        eventsRerunner.rerunEvents();

        verify(gcdService, times(2)).calculateGcd(any(Calculation.class));
    }

    private List<Calculation> twoMockedCalculations() {
        return asList(
                Calculation.builder()
                        .id(1L)
                        .first(10L)
                        .second(20L)
                        .build(),
                Calculation.builder()
                        .id(2L)
                        .first(12L)
                        .second(22L)
                        .build()
        );
    }
}
