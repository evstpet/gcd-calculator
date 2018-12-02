package com.pes.gcdcalculator.application.event;

import com.pes.gcdcalculator.application.event.dto.CalculationResultEvent;
import com.pes.gcdcalculator.domain.vo.Calculation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitEventSender implements EventSender {

    private RabbitTemplate rabbitTemplate;

    private String outcomeExchange;

    private String routingKey;

    @Autowired
    public RabbitEventSender(
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.template.exchange}")
            String outcomeExchange,
            @Value("${client.routing.key}")
            String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.outcomeExchange = outcomeExchange;
        this.routingKey= routingKey;
    }

    @Override
    public void sendEvent(Calculation calculation) {
        CalculationResultEvent result = CalculationResultEvent.builder()
                .id(calculation.getId())
                .first(calculation.getFirst())
                .second(calculation.getSecond())
                .result(calculation.getResult())
                .error(calculation.getError())
                .build();

        rabbitTemplate.convertAndSend(
                outcomeExchange,
                routingKey,
                result
        );
    }
}
