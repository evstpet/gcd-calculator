package com.pes.gcdcalculator;

import com.pes.gcdcalculator.application.event.dto.CalculationRequestEvent;
import com.pes.gcdcalculator.application.event.dto.CalculationResultEvent;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableRabbit
@EnableScheduling
public class GcdCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcdCalculatorApplication.class, args);
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
		jsonConverter.setClassMapper(classMapper());
		return jsonConverter;
	}

	@Bean
	public DefaultClassMapper classMapper() {
		DefaultClassMapper classMapper = new DefaultClassMapper();
		Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("calculationResult", CalculationResultEvent.class);
		idClassMapping.put("calculationRequest", CalculationRequestEvent.class);
		classMapper.setIdClassMapping(idClassMapping);
		classMapper.setTrustedPackages("*");
		return classMapper;
	}
}
