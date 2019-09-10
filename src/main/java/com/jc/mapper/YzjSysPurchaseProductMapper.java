package com.jc.mapper;


import com.jc.model.YzjSysPurchaseProduct;
import com.jc.model.YzjSysResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Repository
public interface YzjSysPurchaseProductMapper {
    List<YzjSysPurchaseProduct> listYzjSysPurchaseProduct(@Param("start") Integer start,
                                             @Param("end") Integer end);
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
