package com.pes.gcdcalculator.domain.vo;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Calculation {
    private Long id;
    private Long first;
    private Long second;
    private Long result;
    private String error;
}
