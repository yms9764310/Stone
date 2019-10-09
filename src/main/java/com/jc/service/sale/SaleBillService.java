package com.jc.service.sale;/*
 * @author 林锦杰
 * @date 2019/8/30 14:16
 * */

import com.jc.model.TProductsyspurchaseproduct;
import com.jc.model.sale.SaleBill;
import com.jc.model.sale.SaleBillDetail;
import com.jc.model.sale.SaleCustomer;

import java.util.List;

public interface SaleBillService {
    //查询全部加分页
    List<SaleBill> listSaleBill(String page, String limit,String creator,Integer bill_no);
    //计数
    int countGetAll();
    //修改信息、
    String updateSaleBill(SaleBill saleBill);
    //查看
    List<SaleBill> loadById(Integer customer_id);
    SaleBill loadById1(Integer id);
    SaleBill loadById2(Integer id);
    //添加订单
    String insertSaleBill(SaleBill saleBill);
    //添加订单详情
    String insertSaleBillDetail(SaleBillDetail saleBillDetail);
    //添加商品
    String insertProduct(TProductsyspurchaseproduct tProductsyspurchaseproduct);
    //查询全部商品
    List<TProductsyspurchaseproduct> loadByType(String model_type);
    //查看订单记录
    SaleBill loadByCusId(Integer id);
    //删除订单
    int deleteSaleBill(int customer_id);
    //审核订单 状态改变
    int updateStateYes(Integer id);
    int updateStateCancel(Integer id);
}
