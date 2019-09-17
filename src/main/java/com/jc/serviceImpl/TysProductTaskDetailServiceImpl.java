package com.jc.serviceImpl;

import com.jc.mapper.*;
import com.jc.model.*;
import com.jc.service.StoreManagementService;
import com.jc.service.TysProductTaskDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    @Resource
    StoreManagementMapper
    storeManagementDao;
    @Resource
    ProduceProcessMapper
    produceProcessDao;
    @Resource
    ProduceProcessDetailMapper
    produceProcessDetailDao;
    @Resource
    StorePutInMapper
    storePutInDao;
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
    public int auditing(int number,int product_id,int task_id) {
        TysProduceBom tysProduceBom = tysProduceBomDao.loadTysProductBomByProductId( product_id );
        List<TysProduceBomDetail> tysProduceBomDetails = tysProduceBomDetailDao.listTysProduceBomDetail( tysProduceBom.getId() );
        for (TysProduceBomDetail tysProduceBomDetail : tysProduceBomDetails) {
            int poductNumber=number*tysProduceBomDetail.getNumber();//需要商品组件的总数量
            int storeNumber = storeManagementDao.count(Integer.valueOf( tysProduceBomDetail.getProduct_id()));//库存总数量
            int  processNumber;//生产数量
            Double outNumber;//领料数量
            StoreCheckOut storeCheckOut = new StoreCheckOut();
            if(tysProduceBomDetail.getModel_type().equals( "半成品" )){
                if (storeNumber>=poductNumber){
                    outNumber= Double.valueOf( poductNumber );//领料单数量
                    //商品id
                    storeCheckOut.setCreator("1");
                    storeCheckOut.setCreate_date( new Date(  ) );
                    storeCheckOut.setModifier( "1" );
                    storeCheckOut.setModify_date( new Date(  ) );
                    storeCheckOut.setNumber( outNumber  );
                    storeCheckOut.setProduct_id( tysProduceBomDetail.getProduct_id() );
                    storePutInDao.insertCheckOut( storeCheckOut );
                    //调用添加领料单的方法
                }
                else {
                    outNumber= Double.valueOf( storeNumber );
                    processNumber=poductNumber-storeNumber;
                    //商品id
                    //调用添加领料单的方法
                    storeCheckOut.setCreator("1");
                    storeCheckOut.setCreate_date( new Date(  ) );
                    storeCheckOut.setModifier( "1" );
                    storeCheckOut.setModify_date( new Date(  ) );
                    storeCheckOut.setNumber( outNumber  );
                    storeCheckOut.setProduct_id( tysProduceBomDetail.getProduct_id() );
                    storePutInDao.insertCheckOut( storeCheckOut );
                    //调用添加加工单的方法
                    ProduceProcess produceProcess = new ProduceProcess();
                    Date date = new Date(  );
                    produceProcess.setCreate_date( date );
                    produceProcess.setProduce_id( task_id );
                    produceProcessDao.saveProduceProcess( produceProcess );
                    ProduceProcessDetail produceProcessDetail = new ProduceProcessDetail();
                    produceProcessDetail.setNumber( processNumber );
                    produceProcessDetail.setProduct_id( tysProduceBomDetail.getProduct_id() );
                    produceProcessDetail.setProcess_id( produceProcess.getId() );
                    produceProcessDetailDao.saveProduceProcessDetail( produceProcessDetail );
                }
            }else {
                outNumber= Double.valueOf( poductNumber );
                tysProduceBomDetail.getProduct_id();//商品id
                storeCheckOut.setCreator("1");
                storeCheckOut.setCreate_date( new Date(  ) );
                storeCheckOut.setModifier( "1" );
                storeCheckOut.setModify_date( new Date(  ) );
                storeCheckOut.setNumber( outNumber  );
                storeCheckOut.setProduct_id( tysProduceBomDetail.getProduct_id() );
                storePutInDao.insertCheckOut( storeCheckOut );
                //调用添加领料单的方法
            }

        }

        return 0;
    }


}
