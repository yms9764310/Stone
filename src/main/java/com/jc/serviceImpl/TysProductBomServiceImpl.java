package com.jc.serviceImpl;

import com.jc.mapper.TysProduceBomMapper;
import com.jc.model.TysProduceBom;
import com.jc.service.TysProductBomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class TysProductBomServiceImpl implements TysProductBomService {
    @Resource
    TysProduceBomMapper
    tysProduceBomDao;
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
        return tysProduceBomDao.loadTysProductBom( id );
    }
}
