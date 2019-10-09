package com.jc.mapper;

import com.jc.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 22
 * 小时: 14
 * 分钟: 42
 *
 * @author 严脱兔
 */
@Repository
public interface ToDoListMapper {
        //根据ID查询
        SysUsersBeans loadById(Integer id);
        //根据用户ID查询
        SysUsers loadByUserId(Integer id);
        //根据加工单ID查询
        ProduceTaskDetail loadByProcessId(Integer id);
        //根据仓库管理
        List<ToDoList> listStoreAll(@Param("start") Integer start,
                                    @Param("end") Integer end,
                                    @Param("name") String name);
        //盘点结果单
        List<ToDoList> listStoreCheck(@Param("start") Integer start,
                                    @Param("end") Integer end,
                                    @Param("name") String name);
        //出库单
        List<ToDoList> listStoreCheckout(@Param("start") Integer start,
                                    @Param("end") Integer end,
                                    @Param("name") String name);
        //入库单
        List<ToDoList> listStorePutIn(@Param("start") Integer start,
                                    @Param("end") Integer end,
                                    @Param("name") String name);
        //出库预警单
        List<ToDoList> listStoreWarn(@Param("start") Integer start,
                                    @Param("end") Integer end,
                                    @Param("name") String name);
        //根据生产订单
        List<ProduceTask> listProcessAll(@Param("start") Integer start,
                                   @Param("end") Integer end,
                                   @Param("name") String name
                                  );

        //查看工作进度
        List<ProduceTask> listWorkAll(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);
        //查看工作流记录
        List<ProduceTask> listWorkflowHistory(Integer id);

        //查看工作进度
        List<ProduceTask> listWorkSpeed(Integer id);

        //查看工作流
        List<ProduceTask> listWorkflow(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);

        //退料单
        List<ToDoList> listProduceBackMaterial(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);
        //物料清单
        List<ToDoList> listProduceBom(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);
        //加工单
        List<ToDoList> listProduceProcess(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);
        //产生任务单
        List<ToDoList> listProduceTask(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);

        //根据系统模块
        List<ToDoList> listSysAll(@Param("start") Integer start,
                                         @Param("end") Integer end,
                                         @Param("name") String name);

        //权限表
        List<ToDoList> listSysAuthority(@Param("start") Integer start,
                                  @Param("end") Integer end,
                                  @Param("name") String name);
        //部门
        List<ToDoList> listSysDepartment(@Param("start") Integer start,
                                  @Param("end") Integer end,
                                  @Param("name") String name);
        //登录表
        List<ToDoList> listSysLoginUser(@Param("start") Integer start,
                                  @Param("end") Integer end,
                                  @Param("name") String name);
        //菜单表
        List<ToDoList> listSysMenus(@Param("start") Integer start,
                                  @Param("end") Integer end,
                                  @Param("name") String name);
        //采购商品
        List<ToDoList> listSysPurchaseProduct(@Param("start") Integer start,
                                  @Param("end") Integer end,
                                  @Param("name") String name);
        //角色表
        List<ToDoList> listSysRole(@Param("start") Integer start,
                                  @Param("end") Integer end,
                                  @Param("name") String name);
        //角色、用户表
        List<ToDoList> listSysUserRole(@Param("start") Integer start,
                                   @Param("end") Integer end,
                                   @Param("name") String name);
        //人员表
        List<ToDoList> listSysUsers(@Param("start") Integer start,
                                   @Param("end") Integer end,
                                   @Param("name") String name);

        //根据财务模块
        List<ToDoList> listAccountAll(@Param("start") Integer start,
                                         @Param("end") Integer end,
                                         @Param("name") String name);
        //付款单
        List<ToDoList> listAccountPayBill(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);
        //应付单
        List<ToDoList> listAccountPayHandleBill(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);
        //收款单
        List<ToDoList> listAccountReceiveBill(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);
        //应收单
        List<ToDoList> listAccountReceiveHandleBill(@Param("start") Integer start,
                                      @Param("end") Integer end,
                                      @Param("name") String name);
        //根据采购模块
        List<ToDoList> listPruchaseAll(@Param("start") Integer start,
                                         @Param("end") Integer end,
                                         @Param("name") String name);
        //采购单
        List<ToDoList> listPruchaseBill(@Param("start") Integer start,
                                       @Param("end") Integer end,
                                       @Param("name") String name);
        //供应商
        List<ToDoList> listPruchaseSupplier(@Param("start") Integer start,
                                       @Param("end") Integer end,
                                       @Param("name") String name);
        //根据销售模块
        List<ToDoList> listSaleAll(@Param("start") Integer start,
                                         @Param("end") Integer end,
                                         @Param("name") String name);
        //销售单
        List<ToDoList> listSaleBill(@Param("start") Integer start,
                                   @Param("end") Integer end,
                                   @Param("name") String name);
        //客户表
        List<ToDoList> listSaleCustome(@Param("start") Integer start,
                                   @Param("end") Integer end,
                                   @Param("name") String name);
        //根据销售模块
        List<ToDoList> listAll(@Param("start") Integer start,
                                   @Param("end") Integer end,
                                   @Param("name") String name);
        //修改信息
        void updateMessage(ToDoList toDoList);
        //修改阈值
        void  updateThreshold(SysUsers sysUsers);
        //获取条数
        int countGetAll();
}
