package com.jc.mapper.sale;

import com.jc.model.sale.SaleCustomer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Repository
public interface SaleCustomerMapper {
    //分页加模糊搜索
    List<SaleCustomer> listCustomer(@Param("start") Integer start,
                                    @Param("end") Integer end,
                                    @Param("name") String name,
                                    @Param("address") String address);
    //计数
    int countGetAll();
    //添加单条信息
    void insertSingleCustomer(SaleCustomer saleCustomer);
    //删除客户信息
    void deleteCustomer(int id);
    //根据ID查询
    SaleCustomer loadById(int id);
    //修改信息、
    void updateCustomer(SaleCustomer saleCustomer);
    //POI导入Excel
    String ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response);
    //插入
    Boolean insertCustomerExcel(SaleCustomer saleCustomer);
}
