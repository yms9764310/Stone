package com.jc.mapper;

import com.jc.model.*;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
public interface StorePutInMapper {
    //分页加搜索
    List<ToDoList> listAll(@Param("start") Integer start,
                           @Param("end") Integer end,
                           @Param("name") String name,
                           @Param("startTime") String startTime,
                           @Param("endTime") String endTime,
                           @Param("typename") String typename);
    //分页加搜索
    List<ToDoList> listStorePutIn(@Param("start") Integer start,
                           @Param("end") Integer end,
                           @Param("name") String name,
                           @Param("startTime") String startTime,
                           @Param("endTime") String endTime,
                           @Param("typename") String typename);
    //分页加搜索
    List<ToDoList> listStoreCheckout(@Param("start") Integer start,
                           @Param("end") Integer end,
                           @Param("name") String name,
                           @Param("startTime") String startTime,
                           @Param("endTime") String endTime,
                           @Param("typename") String typename);
    /**
     * 获取菜单大小
     * */
    int countGetAll();

    //根据ID查询
    SysUsersBeans loadById(Integer id);
    //根据入库ID查询
    StorePutIn loadByPutId(Integer id);
    //根据出库ID查询
    StoreCheckOut loadByCheckId(Integer id);
    //根据商品ID查询
    Store loadByProductId(Integer product_id);
    //根据入库ID查询
    StorePutIn loadByPutInId(Integer id);
    //根据出库ID查询
    StoreCheckOut loadByCheckOutId(Integer id);
    //入库审核通过
    void updatePutInSuccess(StorePutIn storePutIn);
    //入库审核通过且有相应的商品修改库存数量
    void updateStore(StorePutIn storePutIn);
    //出库审核通过且有相应的商品修改库存数量
    void updateStoreCheck(StoreCheckOut storeCheckOut);
    //入库审核通过没有有相应的商品修改库存数量
    void insterStore(StorePutIn storePutIn);
    //入库驳回
    void updatePutInReject(StorePutIn storePutIn);
    //出库审核通过
    void updateCheckOutSuccess(StoreCheckOut storeCheckOut);
    //出库驳回
    void updateCheckOutReject(StoreCheckOut storeCheckOut);
    //添加出库单
    void insertCheckOut(StoreCheckOut storeCheckOut);
    //添加入库单
    void insertStorePutIn(StorePutIn storePutIn);



}
