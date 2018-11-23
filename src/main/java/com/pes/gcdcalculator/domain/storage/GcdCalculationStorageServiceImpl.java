package com.pes.gcdcalculator.domain.storage;

import com.pes.gcdcalculator.domain.vo.Calculation;
import com.pes.gcdcalculator.infrastructure.db.GcdRepository;
import com.pes.gcdcalculator.infrastructure.db.entity.GcdCalculationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Service
public class GcdCalculationStorageServiceImpl implements GcdCalculationStorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcdCalculationStorageService.class);

    private GcdRepository repository;

    @Autowired
    public GcdCalculationStorageServiceImpl(GcdRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Calculation calculation) {
        GcdCalculationEntity calculationEntity = GcdCalculationEntity.builder()
                .id(calculation.getId())
                .first(calculation.getFirst())
                .second(calculation.getSecond())
                .build();

        repository.save(calculationEntity);
        LOGGER.info(calculation + " - successfully saved.");
    }

    @Override
    public void remove(Calculation calculation) {
        repository.deleteById(calculation.getId());
        LOGGER.info(calculation + " - successfully removed.");
    }

    @Override
    public List<Calculation> findCalculations() {
        return stream(repository.findAll().spliterator(), false)
                .map(entity -> Calculation.builder()
                        .id(entity.getId())
                        .first(entity.getFirst())
                        .second(entity.getSecond())
                        .build()
                )
                .collect(toList());
    }
}
