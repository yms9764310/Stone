package com.jc.serviceImpl;/*
 * @author 林锦杰
 * @date 2019/8/30 14:17
 * */

import com.jc.beans.response.PageRange;
import com.jc.mapper.*;
import com.jc.mapper.sale.SaleBillDetailMapper;
import com.jc.mapper.sale.SaleBillMapper;
import com.jc.model.AccountReceiveHandleBill;
import com.jc.model.StoreCheckOut;
import com.jc.model.SysLoginUser;
import com.jc.model.TProductsyspurchaseproduct;
import com.jc.model.sale.SaleBill;
import com.jc.model.sale.SaleBillDetail;
import com.jc.service.sale.SaleBillService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class SaleBillServiceImpl implements SaleBillService {
    @Resource
    private SaleBillMapper saleBillMapper;
    @Resource
    private SysLoginUserMapper sysLoginUserMapper;
    @Resource
    private SaleBillDetailMapper saleBillDetailMapper;
    @Resource
    private AccountReceiveHandleBillMapeer accountReceiveHandleBillMapeer;
    @Resource
    private StorePutInMapper storePutInMapper;
    @Override
    public List<SaleBill> listSaleBill(String page, String limit, String creator, Integer bill_no) {
        PageRange pageRange = new PageRange(page, limit);
        return saleBillMapper.listSaleBill(pageRange.getStart(),pageRange.getEnd(),creator,bill_no);
    }

    @Override
    public int countGetAll() {
        return saleBillMapper.countGetAll();
    }

    @Override
    public String updateSaleBill(SaleBill saleBill) {
        //取到管理员ID
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        SysLoginUser sysLoginUser = sysLoginUserMapper.loadById(id);
        saleBill.setModifier(sysLoginUser.getModifier()+"");
        Date modify_date = new Date();
        saleBill.setModify_date(modify_date);
        saleBill.setState("3");
        saleBillMapper.updateSaleBill(saleBill);
        return "success";
    }

    @Override
    public List<SaleBill> loadById(Integer id) {
        return saleBillMapper.loadById(id);
    }

    @Override
    //审核预览订单
    public  SaleBill loadById1(Integer id) {
        SaleBill saleBill= saleBillMapper.loadById1(id);
        int id1 = saleBill.getId();
        List<SaleBillDetail> saleBillDetailList = saleBillDetailMapper.loadById(id1);
        saleBill.setSaleBillDetailList(saleBillDetailList);
        return saleBill;
    }

    @Override
    //查看已审核订单
    public SaleBill loadById2(Integer id) {
        SaleBill saleBill= saleBillMapper.loadById2(id);
        int id1 = saleBill.getId();
        List<SaleBillDetail> saleBillDetailList = saleBillDetailMapper.loadById1(id1);
        saleBill.setSaleBillDetailList(saleBillDetailList);
        return saleBill;
    }

    @Override
    public String insertSaleBill(SaleBill saleBill) {
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        SysLoginUser sysLoginUser = sysLoginUserMapper.loadById(id);
        Date create_date = new Date();
        saleBill.setCreate_date(create_date);
        saleBill.setModify_date(create_date);
        saleBill.setCreator(sysLoginUser.getCreator()+"");
        saleBill.setModifier(sysLoginUser.getModifier()+"");
        saleBill.setSale_id(sysLoginUser.getCreator());
        saleBill.setState("3");
        //客户ID等于客户表ID
        //saleBill.setCustomer_id(saleCustomer.getId());
        //如果是管理员提交则不可修改,如说是员工提交则可以修改
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        saleBill.setBill_no(rannum + str);
        saleBillMapper.insertSaleBill(saleBill);
        for (SaleBillDetail saleBillDetail:saleBill.getSaleBillDetailList()){
            saleBillDetail.setSale_bill_id(saleBill.getId());
            saleBillMapper.insertSaleBillDetail(saleBillDetail);
        }
        return "success";
    }

    @Override
    public String insertSaleBillDetail(SaleBillDetail saleBillDetail) {
        saleBillMapper.insertSaleBillDetail(saleBillDetail);
        return "success";
    }

    @Override
    public String insertProduct(TProductsyspurchaseproduct tProductsyspurchaseproduct) {
        saleBillMapper.insertProduct(tProductsyspurchaseproduct);
        return "success";
    }

    @Override
    public List<TProductsyspurchaseproduct> loadByType(String model_type) {
        return saleBillMapper.loadByType(model_type);
    }


    @Override
    public SaleBill loadByCusId(Integer id) {
        return saleBillMapper.loadByCusId(id);
    }

    @Override
    public int deleteSaleBill(int customer_id) {
        saleBillMapper.deleteSaleBill(customer_id);
        return customer_id;
    }
    /*
    * 审核订单
    * */
    @Override
    public int updateStateYes(Integer id) {
        saleBillMapper.updateStateYes(id);
        //SaleBill saleBill = saleBillMapper.loadById1(id);
        SaleBill saleBill= saleBillMapper.loadById2(id);
        int saleBillId = saleBill.getId();
        SaleBillDetail saleBillDetail = saleBillDetailMapper.loadById2(saleBillId);
        AccountReceiveHandleBill accountReceiveHandleBill = new AccountReceiveHandleBill();
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        Date date = new Date();
        int uid = user.getId();
        accountReceiveHandleBill.setCreator(uid);
        accountReceiveHandleBill.setCreate_date(date);
        accountReceiveHandleBill.setModifier(uid);
        accountReceiveHandleBill.setModify_date(date);
        accountReceiveHandleBill.setState("24");
        accountReceiveHandleBill.setCommit_user_id(uid);
        accountReceiveHandleBill.setSum_money(saleBill.getSale_money());
        accountReceiveHandleBill.setSource_id(saleBill.getSale_id());
        accountReceiveHandleBill.setSource_type("销售单");
        accountReceiveHandleBill.setReceive_date(saleBill.getDeliver_date());
        accountReceiveHandleBill.setAccount_no("123456456");
        accountReceiveHandleBillMapeer.saveAccountReceiveHandleBill(accountReceiveHandleBill);
        StoreCheckOut storeCheckOut = new StoreCheckOut();
        storeCheckOut.setCreator(uid+"");
        storeCheckOut.setCreate_date(date);
        storeCheckOut.setModifier(uid+"");
        storeCheckOut.setModify_date(date);
        storeCheckOut.setState("5");
        storeCheckOut.setProduct_id(saleBillDetail.getProduct_id());
        storeCheckOut.setNumber(saleBillDetail.getNumber());
        storeCheckOut.setCheckout_date(saleBill.getDeliver_date());
        storeCheckOut.setSource_kind("销售单");
        storeCheckOut.setSource_id("1");
        storeCheckOut.setCheckout_user_id(saleBill.getSale_id());
        storePutInMapper.insertCheckOut(storeCheckOut);
        return 0;
    }

    @Override
    public int updateStateCancel(Integer id) {
        saleBillMapper.updateStateCancel(id);
        return 0;
    }
}
