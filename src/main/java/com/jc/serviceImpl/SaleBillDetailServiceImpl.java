package com.jc.serviceImpl;/*
 * @author 林锦杰
 * @date 2019/9/26 14:20
 * */

import com.jc.mapper.sale.SaleBillDetailMapper;
import com.jc.model.sale.SaleBillDetail;
import com.jc.service.sale.SaleBillDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SaleBillDetailServiceImpl implements SaleBillDetailService {
    @Resource
    private SaleBillDetailMapper saleBillDetailMapper;
    @Override
    public List<SaleBillDetail> loadById(Integer sale_bill_id) {
        return saleBillDetailMapper.loadById(sale_bill_id);
    }

    @Override
    public List<SaleBillDetail> loadById1(Integer sale_bill_id) {
        return saleBillDetailMapper.loadById1(sale_bill_id);
    }

    @Override
    public SaleBillDetail loadById2(Integer sale_bill_id) {
        return saleBillDetailMapper.loadById2(sale_bill_id);
    }

    @Override
    public int deleteSaleBillDetail(Integer sale_bill_id) {
        saleBillDetailMapper.deleteSaleBillDetail(sale_bill_id);
        return sale_bill_id;
    }
}
