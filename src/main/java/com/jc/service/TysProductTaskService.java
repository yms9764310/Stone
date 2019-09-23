package com.jc.service;

import com.jc.model.ProduceTask;

import java.util.List;

public interface TysProductTaskService {
    List<ProduceTask> listProduceTask(String page, String limit);
    int countProduceTask();
    int saveProduceTask(ProduceTask produceTask);
    ProduceTask loadProduceTask(int id);
    int removeProduceTask(int id);
    int updateProduceTask (ProduceTask produceTask);
    int updateProduceTaskAuditing (int id);
    int updateProduceTaskReject (int id);

}
