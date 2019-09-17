package com.jc.service;

import com.jc.model.YzjSysPurchaseProduct;
import com.jc.model.YzjSysResource;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface YzjSysPurchaseProductService {
    List<YzjSysPurchaseProduct> listYzjSysPurchaseProduct(String page, String limit);
    /**
     * 获取菜单大小
     * */
    int countYzjSysPurchaseProduct();
    //删除
    int deleteYzjSysPurchaseProduct(int id);
    //修改
    Integer updateYzjSysPurchaseProduct(YzjSysPurchaseProduct yzjSysPurchaseProduct);
    //根据ID查询
    YzjSysPurchaseProduct loadYzjSysPurchaseProductById(int id);



    //单独创建部门信息
    Integer saveYzjSysPurchaseProduct(YzjSysPurchaseProduct yzjSysPurchaseProduct);
    //批量导入
    String ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response);

}
