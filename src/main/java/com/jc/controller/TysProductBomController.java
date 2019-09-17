package com.jc.controller;

import com.jc.beans.response.IResult;
import com.jc.beans.response.PageResultBean;
import com.jc.beans.response.ResultBean;
import com.jc.model.TProductsyspurchaseproduct;
import com.jc.model.TysProduceBom;
import com.jc.model.TysProduceBomDetail;
import com.jc.service.TysProductBomDetailService;
import com.jc.service.TysProductBomService;
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
@RequestMapping("/T_ProduceBom")
public class TysProductBomController {
    @Resource
    TysProductBomService
    tysProductBomServiceImpl;
    @Autowired
    TysProductBomDetailService
    tysProductBomDetailServiceImpl;
    @RequestMapping(value = "/listProductBom.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult listProduct(String page, String limit){
        List<TysProduceBom> resultData = tysProductBomServiceImpl.listTysProductBom( page,limit );
        return new PageResultBean<Collection<TysProduceBom>>( resultData,tysProductBomServiceImpl.countTysProduceBom());
    }
    @RequestMapping(value = "/getProductBomName.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult getProductBomName(){
        List<TysProduceBom> resultData = tysProductBomServiceImpl.listTysProductBomName();
        return new ResultBean<Collection<TysProduceBom>>(resultData);
    }
    //删除配方
    @RequestMapping(value = "/deleteProductBom.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult deleteProduct(int id){
        tysProductBomDetailServiceImpl.removeTysProductBomDetail( id );
        tysProductBomServiceImpl.removeTysProductBom( id );
        return new ResultBean<String>("success");
    }
    @RequestMapping(value = "/InsertProductBom.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult insertProductBom(@RequestBody TysProduceBom tysProduceBom){
        tysProduceBom.setCreator( 1 );
        tysProduceBom.setModifier( 1 );
        Date date = new Date(  );
        tysProduceBom.setCreate_date( date );
        tysProduceBom.setModify_date( date );
        tysProduceBom.setState( "1" );
        tysProductBomServiceImpl.saveTysProductBom( tysProduceBom );
        for (TysProduceBomDetail tysProduceBomDetail : tysProduceBom.getListBomDetail()) {
            tysProduceBomDetail.setProduct_bom_id( tysProduceBom.getId() );
            tysProductBomDetailServiceImpl.saveTysProductBomDetail( tysProduceBomDetail );
        }
        return new ResultBean<String>("success");
    }
    @RequestMapping(value = "/loadProductBom.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult loadProductBom(int id){
        return new ResultBean<TysProduceBom>( tysProductBomServiceImpl.loadTysProductBom( id ) );
    }
    @RequestMapping(value = "/UpdateProductBom.do",method = RequestMethod.POST)
    @ResponseBody
    public IResult UpdateProductBom(@RequestBody TysProduceBom tysProduceBom){
        tysProductBomServiceImpl.updateTysProductBom( tysProduceBom );
        List<Integer> list = new ArrayList<>(  ) ;
        List<TysProduceBomDetail> tysProduceBomDetails = tysProductBomDetailServiceImpl.listtysproduceDetail( tysProduceBom.getId() );
        List<TysProduceBomDetail> listBomDetail = tysProduceBom.getListBomDetail();
        for (TysProduceBomDetail produceBomDetail : tysProduceBomDetails) {
            list.add( produceBomDetail.getProduct_id() );
        }
        for (TysProduceBomDetail tysProduceBomDetail : listBomDetail) {
            tysProduceBomDetail.setProduct_bom_id(tysProduceBom.getId());
            int id = tysProduceBomDetail.getProduct_id();
            if (list.contains( id )){
                for (TysProduceBomDetail produceBomDetail : tysProduceBomDetails) {
                    if (id==produceBomDetail.getProduct_id()){
                        tysProduceBomDetail.setId( produceBomDetail.getId());
                    }
                }
                tysProductBomDetailServiceImpl.UpdateTysProduceBomDetail( tysProduceBomDetail );
                list.remove((Integer)id);
            }else {
                tysProductBomDetailServiceImpl.saveTysProductBomDetail( tysProduceBomDetail );
            }
        }
        for (Integer integer : list) {
            for (TysProduceBomDetail produceBomDetail : tysProduceBomDetails) {
                if (integer==produceBomDetail.getProduct_id()){
                    tysProductBomDetailServiceImpl.removeProduceBomDetail(produceBomDetail.getId());
                }
            }

        }
        return new ResultBean<String>("success");
    }
    //导入
    @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file) throws IOException, Exception{
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            map = tProductPurchaseServiceImpl.importExcel(file);
//
//        } catch (Exception e) {
//            map.put("status",-1);
//            map.put("data", "导入异常");
//        }
        return null;

    }
}
