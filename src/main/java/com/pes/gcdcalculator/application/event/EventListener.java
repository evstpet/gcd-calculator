package com.pes.gcdcalculator.application.event;

import com.pes.gcdcalculator.application.event.dto.CalculationRequestEvent;
import com.pes.gcdcalculator.application.service.GcdService;
import com.pes.gcdcalculator.domain.storage.GcdCalculationStorageService;
import com.pes.gcdcalculator.domain.vo.Calculation;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

@Component
public class EventListener {

    private GcdService gcdService;

    private GcdCalculationStorageService storageService;

    @Autowired
    public EventListener(
            GcdService gcdService,
            GcdCalculationStorageService storageService
    ) {
        this.gcdService = gcdService;
        this.storageService = storageService;
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("${spring.rabbitmq.template.queue}"),
                    exchange = @Exchange(value = "${client.exchange}", type = TOPIC),
                    key = {"${calculator.routing.key}"}
            )
    })
    public void onMessage(Message<CalculationRequestEvent> message) {
        CalculationRequestEvent request = message.getPayload();
        Calculation calculation = Calculation.builder()
                .id(request.getId())
                .first(request.getFirst())
                .second(request.getSecond())
                .build();

        storageService.save(calculation);
        gcdService.calculateGcd(calculation);
    }
}
