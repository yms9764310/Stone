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

    //查询全部人员
    List<SysUsers> listUsers();

    //查询出库记录
    List<StoreCheckOut> listCheckOut(@Param("start") Integer start,
                                     @Param("end") Integer end,
                                     @Param("startTime") String startTime,
                                     @Param("endTime") String endTime,
                                     @Param("name") String name);
    //查询入库记录
    List<StorePutIn> listPutIn(@Param("start") Integer start,
                               @Param("end") Integer end,
                               @Param("startTime") String startTime,
                               @Param("endTime") String endTime,
                               @Param("name") String name);

    int countGetAll();

    int countGetCheckAll();

    int countGetCheckOutAll();

    int countGetPutInAll();
    //根据商品ID查询
    Store loadByProductId(String product_id);

    SysUsersBeans loadById(Integer Id);
    //根据商品ID查询
    StoreWarn loadByProduct_id(Integer product_id);

    //设置库存预警
    void updateWarn(StoreWarn storeWarn);



}
