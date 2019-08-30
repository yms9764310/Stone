package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.PurchaseSupplierMapper;
import com.jc.model.PurchaseSupplier;
import com.jc.model.SupplierProduct;
import com.jc.model.SysPurchaseProduct;
import com.jc.service.PurchaseSupplierService;
import com.jc.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李文教
 * @date 2019/8/21 17:22
 */
@Service
@Transactional
public class PurchaseSupplierServiceImpl implements PurchaseSupplierService {
    @Autowired
    PurchaseSupplierMapper purchaseSupplierMapper;

    //查询全部供应商以及搜索的功能
    @Override
    public List<PurchaseSupplier> listSupplier(String page, String limit, String creator,String name) {
        PageRange pageRange=new PageRange(page,limit);
        return purchaseSupplierMapper.listSupplier(pageRange.getStart(),pageRange.getEnd(),creator,name);
    }
    //获取菜单大小
    @Override
    public int countGetAll() {
        return purchaseSupplierMapper.countGetAll();
    }


    //添加供应商的功能
    @Override
    public boolean insertSupplier(PurchaseSupplier purchaseSupplier) {
        Date date=new Date();
        purchaseSupplier.setCreateDate(date);
        purchaseSupplier.setModifyDate(date);
        purchaseSupplier.setModifier("1");
        purchaseSupplierMapper.insertSupplier(purchaseSupplier);
        //添加商品表
        SupplierProduct supplierProduct=new SupplierProduct();
        for (SupplierProduct pid : purchaseSupplier.getSupplierProductList()) {
                supplierProduct.setProductId(pid.getProductId());
                supplierProduct.setPurchaseSupplierId(purchaseSupplier.getId());
                supplierProduct.setMaxNumber(pid.getMaxNumber());
                supplierProduct.setPrice(pid.getPrice());
                purchaseSupplierMapper.insertProduct(supplierProduct);
        }
        return true;
    }

    //查询全部商品
    @Override
    public List<SysPurchaseProduct> listSysPurchaseProduct() {
        return purchaseSupplierMapper.listSysPurchaseProduct();
    }



    //查看商品
    @Override
    public List<PurchaseSupplier> loadProductSupplier(Integer id) {
        List<PurchaseSupplier> resultData = purchaseSupplierMapper.loadProductSupplier(id);
        return resultData;
    }

    //删除
    @Override
    public boolean deleteSupplier(Integer id) {
        List<SupplierProduct> list=purchaseSupplierMapper.listProductSupplier(id);
        for (SupplierProduct supplierProduct : list) {
            purchaseSupplierMapper.deletePruchase(supplierProduct.getPurchaseSupplierId());
        }
        purchaseSupplierMapper.deleteSupplier(id);
        return true;
    }

    //根据id获取指定供应商信息
    @Override
    public List<PurchaseSupplier> loadPurchaseSupplier(Integer id) {
        return purchaseSupplierMapper.loadPurchaseSupplier(id);
    }

    //修改指定供应商信息
    @Override
    public boolean updateSupplier(PurchaseSupplier purchaseSupplier) {
        SupplierProduct supplierProduct=new SupplierProduct();
        //数据库数据
        List<Map<String,Object>> list=purchaseSupplierMapper.listPruchaseSupplierProduct(purchaseSupplier.getId());
        Map<Integer,Integer> mapResult=new HashMap<>();//存放主键和商品id
        //遍历数据库数据
        for (Map<String, Object> stringObjectMap : list) {
            mapResult.put((int)stringObjectMap.get("product_id"),(int)stringObjectMap.get("id"));
        }
        //页面传过来的数据
        List<SupplierProduct> productList=purchaseSupplier.getSupplierProductList();
        //对页面数据遍历
        for (SupplierProduct supplierList : productList) {
            if (mapResult.containsKey(supplierList.getProductId())){
                //包含执行update
                int supplierProductIdmapResult=mapResult.get(supplierList.getProductId());
                supplierProduct.setId(supplierProductIdmapResult);
                supplierProduct.setPurchaseSupplierId(purchaseSupplier.getId());
                supplierProduct.setProductId(supplierList.getProductId());
                supplierProduct.setMaxNumber(supplierList.getMaxNumber());
                supplierProduct.setPrice(supplierList.getPrice());
                purchaseSupplierMapper.updateProduct(supplierProduct);
                mapResult.remove(supplierList.getProductId());
            }else{
                supplierProduct.setProductId(supplierList.getProductId());
                supplierProduct.setPurchaseSupplierId(purchaseSupplier.getId());
                supplierProduct.setPrice(supplierList.getPrice());
                supplierProduct.setMaxNumber(supplierList.getMaxNumber());
                purchaseSupplierMapper.insertProduct(supplierProduct);
            }
        }
        for (Integer productId : mapResult.keySet()) {
            int pruchaseId=mapResult.get(productId);
            purchaseSupplierMapper.deletePrudchaseSupplierProduct(pruchaseId);
        }
        Date date=new Date();
        //给创建时间获取当前系统时间
        purchaseSupplier.setModifyDate(date);
        purchaseSupplier.setModifier("1");
        purchaseSupplierMapper.updateSupplier(purchaseSupplier);
        return true;
    }

    //POI导入Excel数据表格
    @Override
    public String ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);

        MultipartFile file = multipartRequest.getFile("upfile");
        if(file.isEmpty()){
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InputStream in =null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=0;i<listob.size();i++){
            List<Object> lo = listob.get(i);
            PurchaseSupplier purchaseSupplier=new PurchaseSupplier();
            SysPurchaseProduct sysPurchaseProduct=new SysPurchaseProduct();
            SupplierProduct supplierProduct=new SupplierProduct();
            //创建人
            purchaseSupplier.setCreator(lo.get(0)+"");
            //创建时间
            purchaseSupplier.setCreateDate(new Date());
            //修改人
            purchaseSupplier.setModifier(lo.get(2)+"");
            //修改时间
            purchaseSupplier.setModifyDate(new Date());
            //状态
            purchaseSupplier.setState(String.valueOf(lo.get(4)));
            List<SysPurchaseProduct> list=purchaseSupplierMapper.listSysPurchaseProduct();
            //判断采购商品表里是否已有添加的商品
            for (SysPurchaseProduct purchaseProduct : list) {
                if (String.valueOf(lo.get(5))!=purchaseProduct.getName()){
                    //商品
                    sysPurchaseProduct.setName(String.valueOf(lo.get(5)));
                }
            }
            //获取采购商品的创建时间
            sysPurchaseProduct.setCreateDate(new Date());
            //获取采购商品的修改时间
            sysPurchaseProduct.setModifyDate(new Date());
            //设置采购商品的创建人
            sysPurchaseProduct.setCreator(1+"");
            //添加采购商品
            purchaseSupplierMapper.insertSysProduct(sysPurchaseProduct);
            //公司名
            purchaseSupplier.setCompanyName(String.valueOf(lo.get(6)));
            //联系人
            purchaseSupplier.setContactName(String.valueOf(lo.get(7)));
            //联系电话
            purchaseSupplier.setContactPhone(String.valueOf(lo.get(8)));
            //公司地址
            purchaseSupplier.setAddress(String.valueOf(lo.get(9)));
            //添加供应商
            purchaseSupplierMapper.insertSupplier(purchaseSupplier);
            //添加商品表
            supplierProduct.setProductId(sysPurchaseProduct.getId());
            supplierProduct.setPurchaseSupplierId(purchaseSupplier.getId());
            supplierProduct.setMaxNumber((BigDecimal) lo.get(10));
            supplierProduct.setPrice((BigDecimal) lo.get(11));
            purchaseSupplierMapper.insertProduct(supplierProduct);
        }
        System.out.println("文件导入成功！");
        return "success";
    }
}
