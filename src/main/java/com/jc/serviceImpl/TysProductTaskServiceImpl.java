package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.TysProductTaskMapper;
import com.jc.model.ProduceTask;
import com.jc.service.TysProductTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class TysProductTaskServiceImpl implements TysProductTaskService {
    @Resource
    TysProductTaskMapper
    tysProductTaskDao;
    @Override
    public List<ProduceTask> listProduceTask(String page, String limit) {
        PageRange pageRange = new PageRange( page,limit );

        return tysProductTaskDao.listProduceTask( pageRange.getStart(),pageRange.getEnd() );
    }

    @Override
    public int countProduceTask() {
        return tysProductTaskDao.countProduceTask();
    }

    @Override
    public int saveProduceTask(ProduceTask produceTask) {
        return tysProductTaskDao.saveProduceTask( produceTask );
    }

    @Override
    public ProduceTask loadProduceTask(int id) {
        return tysProductTaskDao.loadProduceTask( id );
    }

    @Override
    public int removeProduceTask(int id) {
        return tysProductTaskDao.removeProduceTask( id );
    }

    @Override
    public int updateProduceTask(ProduceTask produceTask) {
        return tysProductTaskDao.updateProduceTask( produceTask );
    }

    @Override
    public int updateProduceTaskAuditing(int id) {
        return tysProductTaskDao.updateProduceTaskAuditing( id );
    }

    @Override
    public int updateProduceTaskReject(int id) {
        return tysProductTaskDao.UpdateProductTaskReject( id );
    }
}
