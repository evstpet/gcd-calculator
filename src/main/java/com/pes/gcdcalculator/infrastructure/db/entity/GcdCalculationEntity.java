package com.pes.gcdcalculator.infrastructure.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GcdCalculationEntity {
    @Id
    private Long id;
    private Long first;
    private Long second;
}
