package com.jc.serviceImpl;


import com.jc.beans.response.PageRange;
import com.jc.beans.response.PageResultBean;
import com.jc.mapper.AssignmentWorkMapper;
import com.jc.mapper.ToDoListMapper;
import com.jc.model.*;
import com.jc.service.AssignmentWorkService;
import com.jc.service.ToDoListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 年: 2019
 * 月: 08
 * 日: 22
 * 小时: 22
 * 分钟: 24
 *
 * @author 严脱兔
 */
@Service
@Transactional
public class ToDoListServiceImpl implements ToDoListService {
    @Resource
    private ToDoListMapper toDoListMapper;

    public String updateMessage(ToDoList toDoList) {
        toDoListMapper.updateMessage(toDoList);
        return "success";
    }


    public SysUsersBeans loadById(Integer id) {
        return toDoListMapper.loadById(id);
    }

    public ProduceTaskDetail loadByProcessId(Integer id) {
        return toDoListMapper.loadByProcessId(id);
    }

    public List<ToDoList> listStoreAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        return toDoListMapper.listStoreAll(pageRange.getStart(), pageRange.getEnd(), name);

    }

    public List<ToDoList> listAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        //先获取当前账号的ID,判断是否是主管
        int id = 1;
        List<ToDoList> resultData = new ArrayList<ToDoList>();
        SysUsersBeans sysUsersBeans = toDoListMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管") && sysUsersBeans.getRole_id().equals("1")) {
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                List<ToDoList> listStoreCheck = toDoListMapper.listStoreCheck(pageRange.getStart(), pageRange.getEnd(),name);
                for (ToDoList toDoList : listStoreCheck) {
                    toDoList.setTypename("盘点任务单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listStoreCheckout = toDoListMapper.listStoreCheckout(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listStoreCheckout) {
                    toDoList.setTypename("出库单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listStorePutIn = toDoListMapper.listStorePutIn(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listStorePutIn) {
                    toDoList.setTypename("入库单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listStoreWarn = toDoListMapper.listStoreWarn(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listStoreWarn) {
                    toDoList.setTypename("出库预警单");
                    resultData.add(toDoList);
                }
                return listLimitData(page, limit, resultData);
            } else if (sysUsersBeans.getDepart_id().equals("生产部")) {
                List<ToDoList> listProduceBackMaterial = toDoListMapper.listProduceBackMaterial(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listProduceBackMaterial) {
                    toDoList.setTypename("退料单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listProduceBom = toDoListMapper.listProduceBom(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listProduceBom) {
                    toDoList.setTypename("物料清单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listProduceProcess = toDoListMapper.listProduceProcess(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listProduceProcess) {
                    toDoList.setTypename("加工单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listProduceTask = toDoListMapper.listProduceTask(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listProduceTask) {
                    toDoList.setTypename("产生任务单");
                    resultData.add(toDoList);
                }
                return listLimitData(page, limit, resultData);
            } else if (sysUsersBeans.getDepart_id().equals("系统模块")) {
                List<ToDoList> listSysAuthority = toDoListMapper.listSysAuthority(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSysAuthority) {
                    toDoList.setTypename("权限表");
                    resultData.add(toDoList);
                }
                List<ToDoList> listSysDepartment = toDoListMapper.listSysDepartment(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSysDepartment) {
                    toDoList.setTypename("部门");
                    resultData.add(toDoList);
                }
                List<ToDoList> listSysLoginUser = toDoListMapper.listSysLoginUser(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSysLoginUser) {
                    toDoList.setTypename("登录表");
                    resultData.add(toDoList);
                }
                List<ToDoList> listSysMenus = toDoListMapper.listSysMenus(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSysMenus) {
                    toDoList.setTypename("菜单表");
                    resultData.add(toDoList);
                }
                List<ToDoList> listSysPurchaseProduct = toDoListMapper.listSysPurchaseProduct(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSysPurchaseProduct) {
                    toDoList.setTypename("采购商品");
                    resultData.add(toDoList);
                }
                List<ToDoList> listSysRole = toDoListMapper.listSysRole(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSysRole) {
                    toDoList.setTypename("角色表");
                    resultData.add(toDoList);
                }
                List<ToDoList> listSysUserRole = toDoListMapper.listSysUserRole(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSysUserRole) {
                    toDoList.setTypename("角色/用户表");
                    resultData.add(toDoList);
                }
                List<ToDoList> listSysUsers = toDoListMapper.listSysUsers(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSysUsers) {
                    toDoList.setTypename("人员表");
                    resultData.add(toDoList);
                }
                return listLimitData(page, limit, resultData);
            } else if (sysUsersBeans.getDepart_id().equals("财务部")) {
                List<ToDoList> listAccountPayBill = toDoListMapper.listAccountPayBill(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listAccountPayBill) {
                    toDoList.setTypename("付款单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listAccountPayHandleBill = toDoListMapper.listAccountPayHandleBill(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listAccountPayHandleBill) {
                    toDoList.setTypename("应付单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listAccountReceiveBill = toDoListMapper.listAccountReceiveBill(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listAccountReceiveBill) {
                    toDoList.setTypename("收款单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listAccountReceiveHandleBill = toDoListMapper.listAccountReceiveHandleBill(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listAccountReceiveHandleBill) {
                    toDoList.setTypename("应收单");
                    resultData.add(toDoList);
                }
                return listLimitData(page, limit, resultData);
            } else if (sysUsersBeans.getDepart_id().equals("采购部")) {
                List<ToDoList> listPruchaseBill = toDoListMapper.listPruchaseBill(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listPruchaseBill) {
                    toDoList.setTypename("采购单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listPruchaseSupplier = toDoListMapper.listPruchaseSupplier(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listPruchaseSupplier) {
                    toDoList.setTypename("供应商");
                    resultData.add(toDoList);
                }
                return listLimitData(page, limit, resultData);
            } else if (sysUsersBeans.getDepart_id().equals("销售部")) {
                List<ToDoList> listSaleBill = toDoListMapper.listSaleBill(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSaleBill) {
                    toDoList.setTypename("销售单");
                    resultData.add(toDoList);
                }
                List<ToDoList> listSaleCustome = toDoListMapper.listSaleCustome(pageRange.getStart(), pageRange.getEnd(), name);
                for (ToDoList toDoList : listSaleCustome) {
                    toDoList.setTypename("客户表");
                    resultData.add(toDoList);
                }
                return listLimitData(page, limit, resultData);
            }
        }
        return toDoListMapper.listAll(pageRange.getStart(), pageRange.getEnd(),name);
    }

    public List<ProduceTask> listProcessAll(String page, String limit, String name){
        PageRange pageRange = new PageRange(page, limit);
        //先获取当前账号的ID,判断是否是生产部
        int id = 1;
        List<ProduceTask> resultData = new ArrayList<ProduceTask>();
        SysUsersBeans sysUsersBeans = toDoListMapper.loadById(id);
        if (sysUsersBeans.getDepart_id().equals("财务部")) {
            String user_name =  sysUsersBeans.getUser_name();
            sysUsersBeans.getDepart_id();
            resultData = toDoListMapper.listProcessAll(pageRange.getStart(), pageRange.getEnd(),user_name);
            return resultData;
        }
        return null;
    }


    public List<ToDoList> listSysAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        return toDoListMapper.listSysAll(pageRange.getStart(), pageRange.getEnd(), name);
    }

    @Override
    public List<ProduceTask> listWorkAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        //先获取当前账号的ID,判断是否是生产部
        int id = 1;
        List<ProduceTask> resultData = new ArrayList<ProduceTask>();
        SysUsersBeans sysUsersBeans = toDoListMapper.loadById(id);
        if (sysUsersBeans.getDepart_id().equals("财务部")) {
            String user_name =  sysUsersBeans.getUser_name();
            resultData = toDoListMapper.listWorkAll(pageRange.getStart(), pageRange.getEnd(),user_name);
            return resultData;
        }
        return null;
    }

    @Override
    public List<ProduceTask> listWorkflowHistory(Integer id) {
        List<ProduceTask> produceTasks= new ArrayList<ProduceTask>();
        List<ProduceTask> produceProcesses1 = toDoListMapper.listWorkflowHistory(id);
        for (ProduceTask produceTask : produceProcesses1) {
            produceTask.getEnd_date();
            produceTask.getProcess_type();
            produceTask.getProcess_type();
            produceTasks.add(produceTask);
        }
        return produceTasks;
    }

    @Override
    public List<ProduceTask> listWorkSpeed(Integer id) {
        List<ProduceTask> produceTasks= new ArrayList<ProduceTask>();
        List<ProduceTask> produceProcesses1 = toDoListMapper.listWorkSpeed(id);
        for (ProduceTask produceTask : produceProcesses1) {
            produceTask.getEnd_date();
            produceTask.getProcess_type();
            produceTask.getProcess_type();
            produceTasks.add(produceTask);
        }
        return produceTasks;
    }

    @Override
    public List<ProduceTask> listWorkflow(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        //先获取当前账号的ID,判断是否是生产部
        int id = 1;
        List<ProduceTask> resultData = new ArrayList<ProduceTask>();
        SysUsersBeans sysUsersBeans = toDoListMapper.loadById(id);
//        long current_date = new Date().getTime();
        if (sysUsersBeans.getDepart_id().equals("财务部")) {
            String user_name =  sysUsersBeans.getUser_name();
            resultData = toDoListMapper.listWorkflow(pageRange.getStart(), pageRange.getEnd(),user_name);
//            for (ProduceTask resultDatum : resultData) {
//                long end_date =resultDatum.getEnd_date().getTime();
//                if (current_date>end_date){
//                    resultData.add(resultDatum);
//                }
//            }
            return resultData;
        }
        return null;
    }


    public List<ToDoList> listAccountAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        return toDoListMapper.listAccountAll(pageRange.getStart(), pageRange.getEnd(), name);
    }


    public List<ToDoList> listPruchaseAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        return toDoListMapper.listPruchaseAll(pageRange.getStart(), pageRange.getEnd(), name);
    }


    public List<ToDoList> listSaleAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        return toDoListMapper.listSaleAll(pageRange.getStart(), pageRange.getEnd(), name);
    }

    @Override
    public int countGetAll() {
        return toDoListMapper.countGetAll();
    }

    public List<ToDoList> listLimitData(String page, String limit, List<ToDoList> limitData) {
        List<ToDoList> resultData = new ArrayList<ToDoList>();
        //起始下标
        int fromIndex = Integer.valueOf(limit) * (Integer.valueOf(page) - 1);
        //终止下标
        int intLimit = Integer.valueOf(limit);
        int toIndex = fromIndex + intLimit;
        int size = limitData.size();
        if (toIndex >= size) {
            toIndex = size;
        }
        resultData = limitData.subList(fromIndex, toIndex);
        return resultData;
    }
}
