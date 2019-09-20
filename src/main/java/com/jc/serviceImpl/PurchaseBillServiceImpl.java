package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.PurchaseBillMapper;
import com.jc.model.*;
import com.jc.service.PurchaseBillService;
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


    //查询全部采办事项
    @Override
    public List<PurchaseBill> listPurchaseBill(String page, String limit,String creator) {
        PageRange pageRange=new PageRange(page,limit);
        return purchaseBillMapper.listPurchaseBill(pageRange.getStart(),pageRange.getEnd(),creator);
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
        //创建时间
        purchaseBill.setCreateDate(new Date());
        //修改人
        purchaseBill.setModifier(purchaseBill.getCreator());
        //修改时间
        purchaseBill.setModifyDate(new Date());
        //状态7表示是待采办
        purchaseBill.setState(9+"");
        //状态6表示是待采办事项
        purchaseBill.setIsBill(6+"");
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
            System.out.println(purchaseBill.getPurchaseBillDetailList().get(i).getSysPurchaseProduct().getId());
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

//    @Override
//    public List<PurchaseSupplier> listPurchaseSupplierLike(Integer id ) {
//        return purchaseBillMapper.listPurchaseSupplieLike(id);
//    }

    @Override
    public List<PurchaseBill> listPurchaseBillOrders(String page,String limit) {
        PageRange pageRange=new PageRange(page,limit);
        return purchaseBillMapper.listPurchaseBillOrders(pageRange.getStart(),pageRange.getEnd());
    }

    @Override
    public List<SupplierProduct> listSupplierProduct(String productName, String kind) {
        return purchaseBillMapper.listSupplierProduct(productName,kind);
    }

    @Override
    public List<SupplierProduct> listSupplierPrice(Integer purchaseSupplierId, Integer productId) {
        return purchaseBillMapper.listSupplierPrice(purchaseSupplierId,productId);
    }

    @Override
    public boolean updatePurchaseBillAudit(PurchaseBill purchaseBill) {
        PurchaseBillDetail purchaseBillDetail=new PurchaseBillDetail();
        purchaseBill.setModifier(1+"");
        purchaseBill.setIsBill(5+"");
        purchaseBill.setModifyDate(new Date());
        purchaseBill.setState(12+"");
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
}