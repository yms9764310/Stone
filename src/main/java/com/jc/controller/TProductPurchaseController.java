package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.TProductsyspurchaseproduct;
import com.jc.service.TProductPurchaseService;
import com.jc.service.TysProductBomService;
import com.jc.socket.SocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/T_Produce")
public class TProductPurchaseController {
    // 注入webSocket的处理类
    @Autowired
    private SocketHandler socketHandler;
    @Resource
    TProductPurchaseService
    tProductPurchaseServiceImpl;
    @Resource
    TysProductBomService
    tysProductBomServiceImpl;
    @RequestMapping(value = "/listProduct.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult listProduct(String page, String limit,String name,String kind){
        List<TProductsyspurchaseproduct> resultData = tProductPurchaseServiceImpl.listT_Product_sys_purchase_product( page,limit,name,kind);
        return new PageResultBean<Collection<TProductsyspurchaseproduct>>( resultData,tProductPurchaseServiceImpl.countT_Product_sys_purchase_product( name,kind ));
    }
    @RequestMapping(value = "/deleteProduct.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult deleteProduct(int id){
        tProductPurchaseServiceImpl.removeT_Product_sys_purchase_product( id );
        return new ResultBean<String>("success");
    }
    @RequestMapping(value = "/InsertProduct.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult insertProduct(@RequestBody TProductsyspurchaseproduct tProductsyspurchaseproduct){
        int user_id = tProductsyspurchaseproduct.getCreator();
        if (user_id==1){
            tProductsyspurchaseproduct.setState( "1" );
        }else {
            tProductsyspurchaseproduct.setState( "2" );
        }
        Date date = new Date();
        tProductsyspurchaseproduct.setCreate_date( date );
        tProductsyspurchaseproduct.setModifier( 1 );
        Date date1 = new Date();
        tProductsyspurchaseproduct.setModify_date( date1 );
        tProductPurchaseServiceImpl.saveT_Product_sys_purchase_product( tProductsyspurchaseproduct );

        return new ResultBean<String>("success");
    }
    @RequestMapping(value = "/loadProduct.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult loadProduct(int id){
        TProductsyspurchaseproduct tProductsyspurchaseproduct = tProductPurchaseServiceImpl.loadT_Product_sys_purchase_product( id );
//        if (tProductsyspurchaseproduct.getCreator()==1){
//            ResultBean  result = new ResultBean<TProductsyspurchaseproduct>(tProductPurchaseServiceImpl.loadT_Product_sys_purchase_product( id ));
//        }else {
//            ResultBean  result = new ResultBean<TProductsyspurchaseproduct>(tProductPurchaseServiceImpl.loadT_Product_sys_purchase_product( id ));
//        }
        return new ResultBean<TProductsyspurchaseproduct>( tProductsyspurchaseproduct );
    }
    @RequestMapping(value = "/UpdateProduct.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult UpdateProduct(@RequestBody TProductsyspurchaseproduct tProductsyspurchaseproduct){
        Date date = new Date(  );
        tProductsyspurchaseproduct.setModify_date( date );
        tProductPurchaseServiceImpl.updateT_Product_sys_purchase_product( tProductsyspurchaseproduct );
        return new ResultBean<String>("success");
    }
    //导入
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file) throws IOException, Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = tProductPurchaseServiceImpl.importExcel(file);

        } catch (Exception e) {
            map.put("status",-1);
            map.put("data", "导入异常");
        }
        return map;

    }
    @RequestMapping(value = "/getProductName.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult getProductName(){
        List<TProductsyspurchaseproduct> list = tProductPurchaseServiceImpl.listTProductsyspurchaseproduct();
        return new ResultBean<Collection<TProductsyspurchaseproduct>>( list );
    }
    @RequestMapping(value = "/getProductMaterialName.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult getProductMaterialName(){
        List<TProductsyspurchaseproduct> list = tProductPurchaseServiceImpl.listTProductsyspurchaseproductMaterialName();
        return new ResultBean<Collection<TProductsyspurchaseproduct>>( list );
    }
}
