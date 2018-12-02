package com.pes.gcdcalculator.application.event;

import com.pes.gcdcalculator.application.event.dto.CalculationRequestEvent;
import com.pes.gcdcalculator.application.event.dto.CalculationResultEvent;
import com.pes.gcdcalculator.application.service.GcdService;
import com.pes.gcdcalculator.domain.vo.Calculation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {EventListener.class, RabbitEventSender.class})
public class EventsTests extends AbstractTestNGSpringContextTests {

    @MockBean
    private GcdService gcdService = mock(GcdService.class);

    @MockBean
    private RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);

    private EventListener listener;

    private RabbitEventSender eventSender;

    private static final String OUTCOME_EXCHANGE = "exchange";
    private static final String ROUTING_KEY = "key";

    private static final Long ID = 1L;
    private static final Long FIRST = 11L;
    private static final Long SECOND = 22L;
    private static final Long RESULT = 11L;

    @BeforeClass
    public void setUpClass() {
        listener = new EventListener(gcdService);
        eventSender = new RabbitEventSender(rabbitTemplate, OUTCOME_EXCHANGE, ROUTING_KEY);
    }

    @Test
    public void testEventListener() {
        CalculationRequestEvent receivedEvent = new CalculationRequestEvent();
        receivedEvent.setId(ID);
        receivedEvent.setFirst(FIRST);
        receivedEvent.setSecond(SECOND);
        Message<CalculationRequestEvent> message = new GenericMessage<>(receivedEvent);

        listener.onMessage(message);

        Calculation expectedCalculation = createSimpleCalculation(null);

        verify(gcdService).calculateGcd(expectedCalculation);
    }

    @Test
    public void testEventSender() {
        CalculationResultEvent result = CalculationResultEvent.builder()
                .id(ID)
                .first(FIRST)
                .second(SECOND)
                .result(RESULT)
                .build();

        Calculation calculation = createSimpleCalculation(RESULT);

        eventSender.sendEvent(calculation);

        verify(rabbitTemplate).convertAndSend(OUTCOME_EXCHANGE, ROUTING_KEY, result);
    }

    private static Calculation createSimpleCalculation(Long result) {
        return Calculation.builder()
                .id(ID)
                .first(FIRST)
                .second(SECOND)
                .result(result)
                .build();
    }

}
