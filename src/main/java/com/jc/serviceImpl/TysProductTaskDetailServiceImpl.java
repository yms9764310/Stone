package com.jc.serviceImpl;

import com.jc.mapper.TysProductTaskDetailMapper;
import com.jc.mapper.TysProductTaskMapper;
import com.jc.model.ProduceTaskDetail;
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
}
