package com.pes.gcdcalculator.infrastructure.db;

import com.pes.gcdcalculator.infrastructure.db.entity.GcdCalculationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GcdRepository extends CrudRepository<GcdCalculationEntity, Long> {
}
