package com.jc.service.sale;/*
 * @author 林锦杰
 * @date 2019/8/30 14:16
 * */

import com.jc.model.sale.SaleBill;
import com.jc.model.sale.SaleCustomer;

import java.util.List;

public interface SaleBillService {
    //查询全部加分页
    List<SaleBill> listSaleBill(String page, String limit,Integer creator,Integer bill_no);
    //计数
    int countGetAll();
    //修改信息、
    String updateSaleBill(SaleBill saleBill);
    //查看
    SaleBill loadById(Integer id);
    List<SaleBill> loadById1(int customer_id);
    //添加单个客户
    String insertSaleBill(SaleBill saleBill);
    //查看订单记录
    SaleBill loadByCusId(Integer id);
    //删除订单
    int deleteSaleBill(int customer_id);
    //审核订单 状态改变
    int updateStateYes(int id);
    int updateStateCancel(int id);
}
