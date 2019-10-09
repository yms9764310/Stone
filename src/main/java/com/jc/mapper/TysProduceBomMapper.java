package com.jc.mapper;

import com.jc.model.TysProduceBom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TysProduceBomMapper {
    List<TysProduceBom> listTysProductBom(@Param("start") int start, @Param("end") int end);
    int saveTysProductBom(TysProduceBom tysProduceBom);
   int removeTysProductBom(int id);
   int updateTysProductBom(TysProduceBom tysProduceBom);
   TysProduceBom loadTysProductBom(int id);
   TysProduceBom loadTysProductBomByProductId(int product_id);
   int countTysProduceBom();
    List<TysProduceBom> listTysProductBomName();

    //用于判断是否有配方
}
