package com.jc.mapper;

import com.jc.model.ProduceProcess;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduceProcessMapper {
    int saveProduceProcess(ProduceProcess produceProcess);
}
