package com.jc.mapper;

import com.jc.model.ProduceTask;
import com.jc.model.ProduceTaskDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TysProductTaskDetailMapper {
//    List<ProduceTask> listProduceTask(@Param("start") int start, @Param("end") int end);
//    int countProduceTask();
    int saveProduceTaskDetail(ProduceTaskDetail produceTaskDetail);
    int removeProduceTaskDetail (int product_task_id);
    List<ProduceTaskDetail> listProduceTaskDetail(int product_task_id);
    int updateProduceTaskDetail (ProduceTaskDetail produceTaskDetail);
    int removeTyProductTaskDetail (int product_id);

}
