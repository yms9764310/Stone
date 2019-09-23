package com.jc.mapper;

import com.jc.model.ProduceProcess;
import com.jc.model.ProduceProcessDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduceProcessDetailMapper {
    int saveProduceProcessDetail(ProduceProcessDetail produceProcessDetail);
}
