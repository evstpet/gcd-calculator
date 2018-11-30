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

    @Value("${spring.rabbitmq.template.exchange}")
    private String outcomeExchange;

    @Value("${client.routing.key}")
    private String routingKey;

    @Autowired
    public RabbitEventSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendEvent(Calculation calculation) {
        CalculationResultEvent result = CalculationResultEvent.builder()
                .id(calculation.getId())
                .result(calculation.getResult())
                .error(calculation.getError())
                .build();

        rabbitTemplate.convertAndSend(
                "gcd.calculator.exchange",
                "gcd.calculator.routing.key",
                result
        );
    }
}
