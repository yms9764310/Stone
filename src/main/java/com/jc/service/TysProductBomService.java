package com.jc.service;


import com.jc.model.TysProduceBom;

public interface TysProductBomService {
    int saveTysProductBom(TysProduceBom tysProduceBom);
    int removeTysProductBom(int id);
    int updateTysProductBom(TysProduceBom tysProduceBom);
    TysProduceBom loadTysProductBom(int id);
}
