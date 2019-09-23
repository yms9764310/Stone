package com.jc.serviceImpl;/*
 * @author 林锦杰
 * @date 2019/8/30 14:17
 * */

import com.jc.beans.response.PageRange;
import com.jc.mapper.sale.SaleBillMapper;
import com.jc.model.sale.SaleBill;
import com.jc.service.sale.SaleBillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
@Service
@Transactional
public class SaleBillServiceImpl implements SaleBillService {
    @Resource
    private SaleBillMapper saleBillMapper;
    @Override
    public List<SaleBill> listSaleBill(String page, String limit, Integer creator, Integer bill_no) {
        PageRange pageRange = new PageRange(page, limit);
        return saleBillMapper.listSaleBill(pageRange.getStart(),pageRange.getEnd(),creator,bill_no);
    }

    @Override
    public int countGetAll() {
        return saleBillMapper.countGetAll();
    }

    @Override
    public String updateSaleBill(SaleBill saleBill) {
        saleBillMapper.updateSaleBill(saleBill);
        return "success";
    }

    @Override
    public SaleBill loadById(Integer id) {
        return saleBillMapper.loadById(id);
    }

    @Override
    public List<SaleBill> loadById1(int customer_id) {
        return saleBillMapper.loadById1(customer_id);
    }

    @Override
    public String insertSaleBill(SaleBill saleBill) {
        saleBillMapper.insertSaleBill(saleBill);
        return "success";
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
    public int updateStateYes(int id) {
        saleBillMapper.updateStateYes(id);
        return 0;
    }

    @Override
    public int updateStateCancel(int id) {
        saleBillMapper.updateStateCancel(id);
        return 0;
    }
}
