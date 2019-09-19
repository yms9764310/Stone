package com.jc.service;

import com.jc.model.TysProduceBomDetail;

import java.util.List;
import java.util.Map;

public interface TysProductBomDetailService {
    int saveTysProductBomDetail(TysProduceBomDetail tysProduceBomDetail);
    int removeTysProductBomDetail(int id);
    List<TysProduceBomDetail> listTysProduceBomDetail(int product_bom_id);
    List<TysProduceBomDetail> listtysproduceDetail(int id);
    int UpdateTysProduceBomDetail(TysProduceBomDetail tysProduceBomDetail);

    int removeProduceBomDetail(int product_id);

}
