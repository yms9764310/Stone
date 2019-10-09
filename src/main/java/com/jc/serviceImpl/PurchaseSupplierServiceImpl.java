package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.PurchaseSupplierMapper;
import com.jc.mapper.SysUsersMapper;
import com.jc.mapper.YzjRoleMapper;
import com.jc.mapper.YzjUserRoleMapper;
import com.jc.model.*;
import com.jc.service.PurchaseSupplierService;
import com.jc.util.ExcelUtils;
import org.apache.shiro.SecurityUtils;
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
    @Autowired
    SysUsersMapper sysUsersMapper;
    @Autowired
    YzjUserRoleMapper yzjUserRoleMapper;
    @Autowired
    YzjRoleMapper yzjRoleMapper;

    //查询全部供应商以及搜索的功能
    @Override
    public List<PurchaseSupplier> listSupplier(String page, String limit, String SysProductName, String name, Integer id) {
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int userId=user.getId();
        List<SysUserRole> userRoleByUID = yzjUserRoleMapper.getUserRoleByUID(userId);
        SysRole sysRole = new SysRole();
        for (SysUserRole sysUserRole : userRoleByUID) {
             sysRole = yzjRoleMapper.selectByPrimaryKeyId(sysUserRole.getRole_id());
        }
        if (sysRole.getName().equals("主管")){
            PageRange pageRange=new PageRange(page,limit);
            List<PurchaseSupplier> purchaseSuppliers = purchaseSupplierMapper.listSupplierUser(pageRange.getStart(), pageRange.getEnd(), SysProductName, name);
            return purchaseSuppliers;
        }else {
            PageRange  pageRange=new PageRange(page,limit);
            List<PurchaseSupplier> purchaseSuppliers = purchaseSupplierMapper.listSupplier(pageRange.getStart(), pageRange.getEnd(), SysProductName, name, userId);
            return purchaseSuppliers;
        }
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
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int userId=user.getId();
        purchaseSupplier.setCreator(userId+"");
        purchaseSupplier.setCreateDate(date);
        purchaseSupplier.setModifyDate(date);
        purchaseSupplier.setModifier(userId+"");
        if(purchaseSupplier.getCreator().equals(1+"")){
            purchaseSupplier.setState(1+"");
        }else if (purchaseSupplier.getCreator().equals(2+"")){
            purchaseSupplier.setState(2+"");
        }
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
                if (supplierProduct!=null) {
                    purchaseSupplierMapper.deletePruchase(supplierProduct.getPurchaseSupplierId());
                }else {
                    break;
                }
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
        //不包含的执行删除
        for (Integer productId : mapResult.keySet()) {
            int pruchaseId=mapResult.get(productId);
            purchaseSupplierMapper.deletePrudchaseSupplierProduct(pruchaseId);
        }
        Date date=new Date();
        //给创建时间获取当前系统时间
        purchaseSupplier.setModifyDate(date);
        //添加修改人
        purchaseSupplier.setModifier("1");
        purchaseSupplier.setCreator(1+"");
        if(purchaseSupplier.getCreator().equals(1+"")){
            purchaseSupplier.setState(1+"");
        }else if (purchaseSupplier.getCreator().equals(2+"")){
            purchaseSupplier.setState(2+"");
        }
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
            listob = new ExcelUtils().getBankListByExcel(in,file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=0;i<listob.size();i++){
            List<Object> lo = listob.get(i);
            PurchaseSupplier purchaseSupplier=new PurchaseSupplier();
            SysPurchaseProduct sysPurchaseProduct=new SysPurchaseProduct();
            SupplierProduct supplierProduct=new SupplierProduct();

            //数据库数据,采购商品表
            List<Map<String,Object>> list=purchaseSupplierMapper.listSysProduct();
            //判断采购商品表里是否已有添加的商品
            Map<String,Integer> mapRes=new HashMap<>();//存放主键,商品名
            for (Map<String, Object> stringObjectMap : list) {
                mapRes.put((String) stringObjectMap.get("name"),(int) stringObjectMap.get("id"));
            }
            //数据库数据,供应商表
            List<Map<String,Object>> listExcel=purchaseSupplierMapper.listExcelProduct();
            Map<String,Integer> mapCompany=new HashMap<>();//存放公司名和主键
            Map<String,Integer> mapContact=new HashMap<>();//存放联系人和主键
            for (Map<String, Object> map : listExcel) {
                mapCompany.put((String)map.get("company_name"),(int)map.get("id"));
                mapContact.put((String)map.get("contact_name"),(int)map.get("id"));
            }
            //判断是否包含了商品名称
            if (mapRes.containsKey(lo.get(1))){
                //是
                int mapId=mapRes.get(lo.get(1));

                if (mapCompany.containsKey(lo.get(2))&&mapContact.containsKey(lo.get(3))){
                    //包含
                    int companyId=mapCompany.get(lo.get(2));
                    supplierProduct.setPurchaseSupplierId(companyId);
                }else{//不包含
                    //供应商
                    purchaseSupplier.setName(String.valueOf(lo.get(0)));
                    //创建人
                    purchaseSupplier.setCreator(1+"");
                    //创建时间
                    purchaseSupplier.setCreateDate(new Date());
                    //修改人
                    purchaseSupplier.setModifier(1+"");
                    //修改时间
                    purchaseSupplier.setModifyDate(new Date());
                    //状态
                    if(purchaseSupplier.getCreator().equals(1+"")){
                        purchaseSupplier.setState(1+"");
                    }else if (purchaseSupplier.getCreator().equals(2+"")){
                        purchaseSupplier.setState(2+"");
                    }
                    //公司名
                    purchaseSupplier.setCompanyName(String.valueOf(lo.get(2)));
                    //联系人
                    purchaseSupplier.setContactName(String.valueOf(lo.get(3)));
                    //联系电话
                    purchaseSupplier.setContactPhone(String.valueOf(lo.get(4)));
                    //公司地址
                    purchaseSupplier.setAddress(String.valueOf(lo.get(5)));
                    //添加供应商
                    purchaseSupplierMapper.insertSupplier(purchaseSupplier);
                    supplierProduct.setPurchaseSupplierId(purchaseSupplier.getId());
                }
                //添加商品表
                supplierProduct.setProductId(mapId);
                supplierProduct.setMaxNumber(String.valueOf(lo.get(6)));
                supplierProduct.setPrice(String.valueOf(lo.get(7)));
                purchaseSupplierMapper.insertProduct(supplierProduct);
            }
        }
        System.out.println("文件导入成功！");
        return "success";
    }
}
