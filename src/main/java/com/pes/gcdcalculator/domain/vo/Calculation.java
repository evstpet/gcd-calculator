package com.pes.gcdcalculator.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Calculation {
    private Long id;
    private Long first;
    private Long second;
    private Long result;
    private String error;
}
