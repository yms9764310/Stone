package com.jc.mapper.sale;/*
 * @author 林锦杰
 * @date 2019/8/30 14:15
 * */

import com.jc.model.TProductsyspurchaseproduct;
import com.jc.model.sale.SaleBill;
import com.jc.model.sale.SaleBillDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SaleBillMapper {
    //分页加模糊搜索
    List<SaleBill> listSaleBill(@Param("start") Integer start,
                                @Param("end") Integer end,
                                @Param("creator") String creator,
                                @Param("bill_no") Integer bill_no);
    //计数
    int countGetAll();
    //修改信息、
    void updateSaleBill(SaleBill saleBill);
    //查看
    List<SaleBill> loadById(Integer customer_id);
    SaleBill loadById1(Integer id);
    SaleBill loadById2(Integer id);
    //添加单条信息
    void insertSaleBill(SaleBill saleBill);
    void insertSaleBillDetail(SaleBillDetail saleBillDetail);
    //添加商品
    void insertProduct(TProductsyspurchaseproduct tProductsyspurchaseproduct);
    //查询商品
    List<TProductsyspurchaseproduct> loadByType(String model_type);
    //查看记录
    SaleBill loadByCusId(Integer id);
    //删除订单
    void deleteSaleBill(int customer_id);
    //审核订单
    int updateStateYes(Integer id);
    int updateStateCancel(Integer id);
}
