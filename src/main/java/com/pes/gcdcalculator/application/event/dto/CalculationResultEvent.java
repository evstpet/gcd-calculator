package com.pes.gcdcalculator.application.event.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CalculationResultEvent implements Serializable {
    private Long id;
    private Long first;
    private Long second;
    private Long result;
    private String error;
}
