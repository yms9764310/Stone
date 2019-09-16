package com.jc.serviceImpl;

import com.jc.mapper.TysProduceBomDetailMapper;
import com.jc.mapper.TysProduceBomMapper;
import com.jc.mapper.TysProductTaskDetailMapper;
import com.jc.mapper.TysProductTaskMapper;
import com.jc.model.ProduceTaskDetail;
import com.jc.model.TysProduceBom;
import com.jc.model.TysProduceBomDetail;
import com.jc.service.TysProductTaskDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TysProductTaskDetailServiceImpl implements TysProductTaskDetailService {
    @Resource
    TysProductTaskDetailMapper
    tysProductTaskDetailDao;
    @Resource
    TysProduceBomMapper
    tysProduceBomDao;
    @Resource
    TysProduceBomDetailMapper
    tysProduceBomDetailDao;
    @Override
    public int saveProduceTaskDetail(ProduceTaskDetail produceTaskDetail) {
        return tysProductTaskDetailDao.saveProduceTaskDetail( produceTaskDetail );
    }

    @Override
    public int removeProduceTaskDetail(int product_task_id) {
        return tysProductTaskDetailDao.removeProduceTaskDetail( product_task_id );
    }

    @Override
    public List<ProduceTaskDetail> listProduceTaskDetail(int product_task_id) {

        return tysProductTaskDetailDao.listProduceTaskDetail( product_task_id );

    }

    @Override
    public int updateProduceTaskDetail(ProduceTaskDetail produceTaskDetail) {
        return tysProductTaskDetailDao.updateProduceTaskDetail( produceTaskDetail );
    }

    @Override
    public int removeTyProductTaskDetail(int product_id) {
        return tysProductTaskDetailDao.removeTyProductTaskDetail( product_id );
    }

    @Override
    public int is(int number, int product_id) {
        TysProduceBom tysProduceBom = tysProduceBomDao.loadTysProductBomByProductId( product_id );
        List<TysProduceBomDetail> tysProduceBomDetails = tysProduceBomDetailDao.listTysProduceBomDetail( tysProduceBom.getId() );
        for (TysProduceBomDetail tysProduceBomDetail : tysProduceBomDetails) {
            if(tysProduceBomDetail.getModel_type().equals( "半成品" )){
                //判断库存数量
            }else {
                //添加领料单
            }

        }

        return 0;
    }
    

}
