package com.jc.serviceImpl;

import com.jc.mapper.TysProduceBomDetailMapper;
import com.jc.mapper.TysProduceBomMapper;
import com.jc.model.TysProduceBomDetail;
import com.jc.service.TysProductBomDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TysProductBomDetailImpl implements TysProductBomDetailService {
    @Resource
    TysProduceBomDetailMapper
    tysProduceBomDetailDao;
    @Override
    public int saveTysProductBomDetail(TysProduceBomDetail tysProduceBomDetail) {
        return tysProduceBomDetailDao.saveTysProduceBomDetail( tysProduceBomDetail );
    }

    @Override
    public int removeTysProductBomDetail(int id) {
        return tysProduceBomDetailDao.removeTysProduceBomDetail( id );
    }

    @Override
    public List<TysProduceBomDetail> listTysProduceBomDetail(int product_bom_id) {
        return tysProduceBomDetailDao.listTysProduceBomDetail( product_bom_id );
    }

    @Override
    public List<TysProduceBomDetail> listtysproduceDetail(int id) {
        return tysProduceBomDetailDao.listtysproduceDetail( id );
    }

    @Override
    public int UpdateTysProduceBomDetail(TysProduceBomDetail tysProduceBomDetail) {
        return tysProduceBomDetailDao.UpdateTysProduceBomDetail( tysProduceBomDetail );
    }

    @Override
    public int removeProduceBomDetail(int product_id) {
        return tysProduceBomDetailDao.removeProduceBomDetail( product_id );
    }
}
