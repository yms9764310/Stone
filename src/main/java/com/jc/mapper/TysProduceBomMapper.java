package com.jc.mapper;

import com.jc.model.TysProduceBom;
import org.springframework.stereotype.Repository;

@Repository
public interface TysProduceBomMapper {
    int saveTysProductBom(TysProduceBom tysProduceBom);
   int removeTysProductBom(int id);
   int updateTysProductBom(TysProduceBom tysProduceBom);
   TysProduceBom loadTysProductBom(int id);
}
