package com.jc.mapper;

import com.jc.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 40
 *
 * @author 严脱兔
 */
@Repository
public interface StoreManagementMapper {
    //分页加搜索
    List<Store> listAll(@Param("start") Integer start,
                           @Param("end") Integer end,
                           @Param("name")String name);

    //分页加搜索
    List<StoreCheck> listCheckAll(@Param("start") Integer start,
                             @Param("end") Integer end,
                             @Param("startTime") String startTime,
                             @Param("endTime") String endTime);

    //分页加搜索
    List<StoreCheck> listCheckWith(@Param("start") Integer start,
                                  @Param("end") Integer end,
                                  @Param("startTime") String startTime,
                                  @Param("endTime") String endTime,
                                  @Param("name") String name);

    //查询全部人员
    List<SysUsers> listUsers(@Param("department_id") String department_id);

    //查询出库记录
    List<StoreCheckOut> listCheckOut(@Param("start") Integer start,
                                     @Param("end") Integer end,
                                     @Param("startTime") String startTime,
                                     @Param("endTime") String endTime,
                                     @Param("name") String name,
                                     @Param("source_kind") String source_kind);
    //查询入库记录
    List<StorePutIn> listPutIn(@Param("start") Integer start,
                               @Param("end") Integer end,
                               @Param("startTime") String startTime,
                               @Param("endTime") String endTime,
                               @Param("name") String name,
                               @Param("source_type") String source_type);
    //统计商品库存量
    int count(Integer product_id);

    int countGetAll();

    int countGetCheckAll();

    int countGetCheckOutAll();

    int countGetPutInAll();
    //根据盘点单的ID去明细里面查询
    List<StoreCheckTaskDetail> loadByCheckId(Integer id);
    //删除盘点任务
    void deleteCheckTask(Integer id);
    //删除盘点明细任务
    void deleteCheckTaskDetail(Integer id);
    //修改盘点任务
    void updateCountingTask(StoreCheck storeCheck);
    //确定盘点明细
    void SureCountingTask(StoreCheckTaskDetail StoreCheckTaskDetail);
    //修改盘点明细
    void updateCountingTaskDetail(StoreCheckTaskDetail storeCheckTaskDetail);

    void insertCheckTask(StoreCheck storeCheck);

    void insertCheckTaskDetail(StoreCheckTaskDetail storeCheckTaskDetail);

    //根据商品ID查询
    Store loadByProductId(String product_id);

    SysUsersBeans loadById(Integer Id);
    //根据商品ID查询
    StoreWarn loadByProduct_id(Integer product_id);

    StoreCheck loadByCheck_Id(Integer id);

    //设置库存预警
    void updateWarn(StoreWarn storeWarn);



}
