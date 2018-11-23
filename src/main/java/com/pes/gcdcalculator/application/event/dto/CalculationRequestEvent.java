package com.pes.gcdcalculator.application.event.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CalculationRequestEvent implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private Long first;
    @NotNull
    private Long second;
}
