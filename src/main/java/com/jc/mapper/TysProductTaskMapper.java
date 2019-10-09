package com.jc.mapper;

import com.jc.model.ProduceTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TysProductTaskMapper {
    List<ProduceTask> listProduceTask(@Param("start") int start, @Param("end") int end);
    int countProduceTask();
    int saveProduceTask(ProduceTask produceTask);
    ProduceTask loadProduceTask(int id);
    int removeProduceTask(int id);
    int updateProduceTask (ProduceTask produceTask);
    int updateProduceTaskAuditing (int id);
    int UpdateProductTaskReject (int id);

}
