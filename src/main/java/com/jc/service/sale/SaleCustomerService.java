package com.jc.service.sale;

import com.jc.model.sale.SaleCustomer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

public interface SaleCustomerService {
    //查询全部加分页
    List<SaleCustomer> listCustomer(String page, String limit,String name,String address);
    //计数
    int countGetAll();
    //添加单个客户
    String insertSingleCustomer(SaleCustomer saleCustomer);
    //删除客户信息
    int deleteCustomer(int id);
    //根据ID查询
    SaleCustomer loadById(int id);
    //修改客户信息
    String updateCustomer(SaleCustomer saleCustomer);
    //插入信息
    public boolean insertCustomerExcel(SaleCustomer saleCustomer);
    //POI导入
    String ajaxUploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception;
}
