package com.jc.mapper;

import com.jc.model.TysProduceBomDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TysProduceBomDetailMapper {
    int saveTysProduceBomDetail(TysProduceBomDetail tysProduceBomDetail);
    int removeTysProduceBomDetail(int product_bom_id);
    int removeProduceBomDetail(int product_id);
    List<TysProduceBomDetail> listTysProduceBomDetail(int product_bom_id);
    List<TysProduceBomDetail> listtysproduceDetail(int id);
    int UpdateTysProduceBomDetail(TysProduceBomDetail tysProduceBomDetail);

}
