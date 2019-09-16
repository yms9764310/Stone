package com.jc.service;

import com.jc.model.ProduceTask;
import com.jc.model.ProduceTaskDetail;

import java.util.List;

public interface TysProductTaskDetailService {
    int saveProduceTaskDetail(ProduceTaskDetail produceTaskDetail);
    int removeProduceTaskDetail (int product_task_id);
    List<ProduceTaskDetail> listProduceTaskDetail(int product_task_id);
    int updateProduceTaskDetail (ProduceTaskDetail produceTaskDetail);
    int removeTyProductTaskDetail (int product_id);
    int is (int number,int product_id);

}
