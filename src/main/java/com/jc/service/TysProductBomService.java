package com.jc.service;


import com.jc.model.TysProduceBom;

import java.util.List;

public interface TysProductBomService {
    int saveTysProductBom(TysProduceBom tysProduceBom);
    int removeTysProductBom(int id);
    int updateTysProductBom(TysProduceBom tysProduceBom);
    TysProduceBom loadTysProductBom(int id);
    List<TysProduceBom> listTysProductBom(String page, String limit);
    List<TysProduceBom> listTysProductBomName();
    int countTysProduceBom();


}
