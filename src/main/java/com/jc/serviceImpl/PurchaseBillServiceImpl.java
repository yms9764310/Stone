package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.*;
import com.jc.model.*;
import com.jc.service.PurchaseBillService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lwj
 * @version 1.0
 * @date 2019/9/4 17:03
 */
@Service
@Transactional
public class PurchaseBillServiceImpl implements PurchaseBillService {
    @Autowired
    PurchaseBillMapper purchaseBillMapper;
    @Autowired
    SysUsersMapper sysUsersMapper;
    @Autowired
    YzjUserRoleMapper yzjUserRoleMapper;
    @Autowired
    YzjRoleMapper yzjRoleMapper;
    @Autowired
    StorePutInMapper storePutInMapper;


    //查询全部采办事项
    @Override
    public List<PurchaseBill> listPurchaseBill(String page, String limit,Integer creator,String creatorName) {
        SysLoginUser sysLoginUser=(SysLoginUser)SecurityUtils.getSubject().getPrincipal();
        int creatorId=sysLoginUser.getId();
        final List<SysUserRole> userRoleByUID = yzjUserRoleMapper.getUserRoleByUID(creatorId);
        SysRole sysRole=new SysRole();
        for (SysUserRole sysUserRole : userRoleByUID) {
            sysRole=yzjRoleMapper.selectByPrimaryKeyId(sysUserRole.getRole_id());
        }
        if (sysRole.getName().equals("主管")){
            PageRange pageRange=new PageRange(page,limit);
            List<PurchaseBill> purchaseBills = purchaseBillMapper.listPurchaseBill(pageRange.getStart(), pageRange.getEnd(),creatorName);
            return purchaseBills;
        }else{
            PageRange pageRange=new PageRange(page,limit);
            List<PurchaseBill> purchaseBills = purchaseBillMapper.listPurchaseBillUser(pageRange.getStart(), pageRange.getEnd(), creatorId,creatorName);
            return purchaseBills;
        }
    }
//获取采办菜单大小
    @Override
    public int countGet() {
        return purchaseBillMapper.countGet();
    }
//查询全部待审核事项
    @Override
    public List<PurchaseBill> listPurchaseCheck(String page, String limit) {
        PageRange pageRange=new PageRange(page,limit);
        return purchaseBillMapper.listPurchaseCheck(pageRange.getStart(),pageRange.getEnd());
    }
//获取待审核菜单大小
    @Override
    public int countGetAll() {
        return purchaseBillMapper.countGetAll();
    }
//查询采购单的详细内容
    @Override
    public List<PurchaseBillDetail> listPurchaseBillDetail() {
        return purchaseBillMapper.listPurchaseBillDetail();
    }
//查询商品表全部商品
    @Override
    public List<SysPurchaseProduct> listPurchaseProduct() {
        return purchaseBillMapper.listSysPurchaseProduct();
    }

    //添加采办事项
    @Override
    public Boolean insertPurchaseBill(PurchaseBill purchaseBill) {
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int userId=user.getId();
        purchaseBill.setCreator(userId+"");
        //创建时间
        purchaseBill.setCreateDate(new Date());
        //修改人
        purchaseBill.setModifier(userId+"");
        //修改时间
        purchaseBill.setModifyDate(new Date());
        //状态5表示是待采办
        purchaseBill.setState(3+"");
        //状态11表示是待采办事项
        purchaseBill.setIsBill(11+"");
        purchaseBillMapper.insertPurchaseBill(purchaseBill);

        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
        for (PurchaseBillDetail billDetail : purchaseBill.getPurchaseBillDetailList()) {
            purchaseBillDetail.setBillId(purchaseBill.getId()+"");
            purchaseBillDetail.setValue(billDetail.getValue());
            purchaseBillMapper.insertPurchaseBillDetail(purchaseBillDetail);
        }
        return true;
    }

    //根据id查询采办事项
    @Override
    public PurchaseBill loadPurchaseBill(Integer id) {
        PurchaseBill purchaseBill = purchaseBillMapper.loadPurchaseBill(id);
        for (int i=0;i<purchaseBill.getPurchaseBillDetailList().size();i++){
            //System.out.println(purchaseBill.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().getId());
            List<SupplierProduct> purchaseSuppliers = purchaseBillMapper.listPurchaseSupplieLike(purchaseBill.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().getId());
            purchaseBill.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().setSupplierProductList(purchaseSuppliers);
        }
        return purchaseBill;
    }

    //删除采办事项
    @Override
    public boolean deletePurchaseBill(Integer id) {
        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
        purchaseBillDetail.setBillId(id+"");
        purchaseBillMapper.deletePurchaseBillDetail(Integer.parseInt(purchaseBillDetail.getBillId()));
        purchaseBillMapper.deletePurchaseBill(id);
        return true;
    }

    //编辑采办事项
    @Override
    public boolean updatePurchaseBill(PurchaseBill purchaseBill) {
//        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
//        //数据库数据
//        List<Map<String,Object>> list=purchaseBillMapper.listBillDeatil(purchaseBill.getId());
//        Map<Integer,Integer> mapRes=new HashMap<>();//存放商品id和主键
//        for (Map<String, Object> stringObjectMap : list) {
//            mapRes.put((int)stringObjectMap.get("product_id"),(int)stringObjectMap.get("id"));
//        }
//        //页面数据
//        List<PurchaseBillDetail> listBill=purchaseBill.getPurchaseBillDetailList();
//       //对页面数据遍历
//        for (PurchaseBillDetail billDetail : listBill) {
//            if (mapRes.containsKey(billDetail.getValue())){
//                //包含执行update
//                int id=mapRes.get(billDetail.getValue());
//                purchaseBillDetail.setId(id);
//                purchaseBillDetail.setValue(billDetail.getValue());
//                purchaseBillMapper.updatePurchaseBillDetail(purchaseBillDetail);
//                mapRes.remove(billDetail.getProductId());
//            }else{
//                purchaseBillDetail.setValue(billDetail.getValue());
//                purchaseBillDetail.setBillId(purchaseBill.getId()+"");
//                purchaseBillMapper.insertPurchaseBillDetail(purchaseBillDetail);
//            }
//        }
//        //不包含执行删除
//        for (Integer keyId : mapRes.keySet()) {
//            int pid=mapRes.get(keyId);
//            purchaseBillMapper.deletePurchaseBillDetail(pid);
//        }
        purchaseBill.setModifyDate(new Date());
        purchaseBill.setModifier(1+"");
        purchaseBillMapper.updatePurchaseBill(purchaseBill);
        purchaseBillMapper.deletePurchaseBillDetail(purchaseBill.getId());
        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
        for (PurchaseBillDetail billDetail : purchaseBill.getPurchaseBillDetailList()) {
            purchaseBillDetail.setValue(billDetail.getValue());
            purchaseBillDetail.setBillId(purchaseBill.getId()+"");
            purchaseBillMapper.insertPurchaseBillDetail(purchaseBillDetail);
        }

        return true;
    }

    @Override
    public List<SysUsers> listSysUsersName() {
        return purchaseBillMapper.listSysUsersName();
    }


    //查询全部采购单
    @Override
    public List<PurchaseBill> listPurchaseBillOrders(String page,String limit) {
        PageRange pageRange=new PageRange(page,limit);
        return purchaseBillMapper.listPurchaseBillOrders(pageRange.getStart(),pageRange.getEnd());
    }

    //查询已审核的商品
    @Override
    public List<PurchaseBillDetail> listBillDetailAudited() {
        return purchaseBillMapper.listBillDetailAudited();
    }

    //商品查询
    @Override
    public List<SupplierProduct> listSupplierProduct(String productName, String kind) {
        return purchaseBillMapper.listSupplierProduct(productName,kind);
    }

    //修改采购单详情
    @Override
    public List<SupplierProduct> listSupplierPrice(Integer purchaseSupplierId, Integer productId) {
        return purchaseBillMapper.listSupplierPrice(purchaseSupplierId,productId);
    }

    //审核
    @Override
    public boolean updatePurchaseBillAudit(PurchaseBill purchaseBill) {
        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
        purchaseBill.setModifier(1+"");
        purchaseBill.setIsBill(11+"");
        purchaseBill.setModifyDate(new Date());
        purchaseBill.setState(5+"");
        for (PurchaseBillDetail billDetail : purchaseBill.getPurchaseBillDetailList()) {
            purchaseBillDetail.setProductId(billDetail.getProductId());
            purchaseBillDetail.setSupplierId(billDetail.getSupplierId());
            purchaseBillDetail.setBillId(purchaseBill.getId()+"");
            purchaseBillDetail.setPrice(billDetail.getPrice());
            purchaseBillDetail.setNumber(billDetail.getNumber());
            purchaseBillDetail.setSumMoney(billDetail.getSumMoney());
            purchaseBillMapper.updatePurchaseBillDetailAudit(purchaseBillDetail);
        }
        purchaseBillMapper.updatePurchaseBillAudit(purchaseBill);
        return true;
    }

    //待完成，根据id查询
    @Override
    public PurchaseBill loadPurchaseBillCompleted(Integer id) {
        PurchaseBill purchaseBillCompleted = purchaseBillMapper.loadPurchaseBillCompleted(id);
        for (int i=0;i<purchaseBillCompleted.getPurchaseBillDetailList().size();i++){
            //System.out.println(purchaseBill.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().getId());
            List<SupplierProduct> purchaseSuppliers = purchaseBillMapper.listPurchaseSupplieLike(purchaseBillCompleted.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().getId());
            purchaseBillCompleted.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().setSupplierProductList(purchaseSuppliers);
        }
        return purchaseBillCompleted;
    }

    //待完成,创建订单
    @Override
    public boolean updateBillComplete(PurchaseBill purchaseBill) {
        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
        purchaseBill.setModifier(1+"");
        purchaseBill.setIsBill(12+"");
        purchaseBill.setModifyDate(new Date());
        purchaseBill.setState(3+"");
        for (PurchaseBillDetail billDetail : purchaseBill.getPurchaseBillDetailList()) {
            purchaseBillDetail.setProductId(billDetail.getProductId());
            purchaseBillDetail.setSupplierId(billDetail.getSupplierId());
            purchaseBillDetail.setBillId(purchaseBill.getId()+"");
            purchaseBillDetail.setPrice(billDetail.getPrice());
            purchaseBillDetail.setNumber(billDetail.getNumber());
            purchaseBillDetail.setSumMoney(billDetail.getSumMoney());
            purchaseBillMapper.updatePurchaseBillDetailAudit(purchaseBillDetail);
        }
        purchaseBillMapper.updatePurchaseBillAudit(purchaseBill);
        return true;
    }

    //待完成的编辑
    @Override
    public boolean updateCompletedBill(PurchaseBill purchaseBill) {
        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
        purchaseBill.setModifier(1+"");
        purchaseBill.setIsBill(11+"");
        purchaseBill.setModifyDate(new Date());
        purchaseBill.setState(5+"");
        for (PurchaseBillDetail billDetail : purchaseBill.getPurchaseBillDetailList()) {
            purchaseBillDetail.setProductId(billDetail.getProductId());
            purchaseBillDetail.setSupplierId(billDetail.getSupplierId());
            purchaseBillDetail.setBillId(purchaseBill.getId()+"");
            purchaseBillDetail.setPrice(billDetail.getPrice());
            purchaseBillDetail.setNumber(billDetail.getNumber());
            purchaseBillDetail.setSumMoney(billDetail.getSumMoney());
            purchaseBillMapper.updatePurchaseBillDetailAudit(purchaseBillDetail);
        }
        purchaseBillMapper.updatePurchaseBillAudit(purchaseBill);
        return true;
    }

    //根据id获取采购单的信息
    @Override
    public PurchaseBill loadBillOrders(Integer id) {
        PurchaseBill billOrders = purchaseBillMapper.loadPurchaseBillCompleted(id);
        for (int i=0;i<billOrders.getPurchaseBillDetailList().size();i++){
            //System.out.println(purchaseBill.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().getId());
            List<SupplierProduct> purchaseSuppliers = purchaseBillMapper.listPurchaseSupplieLike(billOrders.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().getId());
            billOrders.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().setSupplierProductList(purchaseSuppliers);
        }
        return billOrders;
    }

    //采购单的编辑
    @Override
    public boolean updateBillOrders(PurchaseBill purchaseBill) {
        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
        purchaseBill.setModifier(1+"");
        purchaseBill.setIsBill(12+"");
        purchaseBill.setModifyDate(new Date());
        purchaseBill.setState(3+"");
        for (PurchaseBillDetail billDetail : purchaseBill.getPurchaseBillDetailList()) {
            purchaseBillDetail.setProductId(billDetail.getProductId());
            purchaseBillDetail.setSupplierId(billDetail.getSupplierId());
            purchaseBillDetail.setBillId(purchaseBill.getId()+"");
            purchaseBillDetail.setPrice(billDetail.getPrice());
            purchaseBillDetail.setNumber(billDetail.getNumber());
            purchaseBillDetail.setSumMoney(billDetail.getSumMoney());
            purchaseBillMapper.updatePurchaseBillDetailAudit(purchaseBillDetail);
        }
        purchaseBillMapper.updatePurchaseBillAudit(purchaseBill);
        return true;
    }

    //采购单的审核,根据id获取信息
    @Override
    public PurchaseBill loadPurchaseBillOrders(Integer id) {
        PurchaseBill PurchaseBillOrders = purchaseBillMapper.loadPurchaseBillCompleted(id);
        for (int i=0;i<PurchaseBillOrders.getPurchaseBillDetailList().size();i++){
            //System.out.println(purchaseBill.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().getId());
            List<SupplierProduct> purchaseSuppliers = purchaseBillMapper.listPurchaseSupplieLike(PurchaseBillOrders.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().getId());
            PurchaseBillOrders.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().setSupplierProductList(purchaseSuppliers);
        }
        return PurchaseBillOrders;
    }

    //采购单审核通过
    @Override
    public boolean updatePurchaseBillOrders(PurchaseBill purchaseBill) {
        //当前登入人的id
        SysLoginUser sysLoginUser=(SysLoginUser)SecurityUtils.getSubject().getPrincipal();
        int creatorId=sysLoginUser.getId();
        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
        purchaseBill.setModifier(creatorId+"");
        //状态12表示是采购单
        purchaseBill.setIsBill(12+"");
        purchaseBill.setModifyDate(new Date());
        //表示审核通过，但未完成
        purchaseBill.setState(5+"");
        for (PurchaseBillDetail billDetail : purchaseBill.getPurchaseBillDetailList()) {
            purchaseBillDetail.setProductId(billDetail.getProductId());
            purchaseBillDetail.setSupplierId(billDetail.getSupplierId());
            purchaseBillDetail.setBillId(purchaseBill.getId()+"");
            purchaseBillDetail.setPrice(billDetail.getPrice());
            purchaseBillDetail.setNumber(billDetail.getNumber());
            purchaseBillDetail.setSumMoney(billDetail.getSumMoney());
            purchaseBillMapper.updatePurchaseBillDetailAudit(purchaseBillDetail);
            //添加入库单
            StorePutIn storePutIn=new StorePutIn();
            storePutIn.setProduct_id(billDetail.getProductId());
            storePutIn.setCreator(creatorId+"");
            storePutIn.setCreate_date(new Date());
            storePutIn.setModifier(creatorId+"");
            storePutIn.setSource_type("采购单");
            storePutIn.setModify_date(new Date());
            storePutIn.setPut_in_number(Double.valueOf(billDetail.getNumber()));
            storePutIn.setPut_in_user_id(creatorId+"");
            storePutIn.setPut_id_date(purchaseBill.getPutInDate());
            storePutInMapper.insertStorePutIn(storePutIn);
        }
        purchaseBillMapper.updatePurchaseBillAudit(purchaseBill);
        return true;
    }

    //月统计金额、数量、种类
    @Override
    public List<PurchaseBillDetail> countPurchase(Integer yearDate) {
        List<PurchaseBillDetail> purchaseBillDetails = purchaseBillMapper.countPurchase(yearDate);
        return purchaseBillDetails;
    }

    //根据商品种类月统计金额、数量
    @Override
    public List<PurchaseBillDetail> countPurchaseProduct(Integer productId,Integer yearDate) {
        List<PurchaseBillDetail> purchaseBillDetails = purchaseBillMapper.countPurchaseProduct(productId,yearDate);
        return purchaseBillDetails;
    }

    @Override
    public List<PurchaseBillDetail> countPurchaseYear() {
        List<PurchaseBillDetail> year = purchaseBillMapper.countPurchaseYear();
        return year;
    }

    @Override
    public List<PurchaseBillDetail> countPurchaseProductYear(Integer productId) {
        List<PurchaseBillDetail> yearProduct = purchaseBillMapper.countPurchaseProductYear(productId);
        return yearProduct;
    }
}
