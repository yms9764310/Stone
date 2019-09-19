package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.TysProduceBomDetailMapper;
import com.jc.mapper.TysProduceBomMapper;
import com.jc.model.TysProduceBom;
import com.jc.model.TysProduceBomDetail;
import com.jc.service.TysProductBomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TysProductBomServiceImpl implements TysProductBomService {
    @Resource
    TysProduceBomMapper
    tysProduceBomDao;
    @Resource
    TysProduceBomDetailMapper
    tysProduceBomDetailDao;
    @Override
    public int saveTysProductBom(TysProduceBom tysProduceBom) {
        return tysProduceBomDao.saveTysProductBom( tysProduceBom );
    }

    @Override
    public int removeTysProductBom(int id) {
        return tysProduceBomDao.removeTysProductBom( id );
    }

    @Override
    public int updateTysProductBom(TysProduceBom tysProduceBom) {
        return tysProduceBomDao.updateTysProductBom( tysProduceBom );
    }

    @Override
    public TysProduceBom loadTysProductBom(int id) {
        List<TysProduceBomDetail> tysProduceBomDetails = tysProduceBomDetailDao.listTysProduceBomDetail( id );
        TysProduceBom tysProduceBom = tysProduceBomDao.loadTysProductBom( id );
        tysProduceBom.setListBomDetail( tysProduceBomDetails );
        return tysProduceBom;
    }

    @Override
    public List<TysProduceBom> listTysProductBom(String page, String limit) {
        PageRange pageRange = new PageRange(page, limit);
        return tysProduceBomDao.listTysProductBom(pageRange.getStart(),pageRange.getEnd());
    }

    @Override
    public List<TysProduceBom> listTysProductBomName() {
        return tysProduceBomDao.listTysProductBomName();
    }

    @Override
    public int countTysProduceBom() {
        return tysProduceBomDao.countTysProduceBom();
    }


}
