package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.AccountPayBillMapper;
import com.jc.model.AccountPayBill;
import com.jc.service.AccountPayBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountPayBillServiceImpl implements AccountPayBillService {
    @Autowired
    private AccountPayBillMapper accountPaylBillMapper;
    @Override
    public List<AccountPayBill> listAccountPayBill(String page, String limit) {
        PageRange paged= new PageRange(page, limit);
        return accountPaylBillMapper.selectListPayBill(paged.getStart(),paged.getEnd());
    }

    @Override
    public int countAccountPayBill() {
        return accountPaylBillMapper.countAccountPayBill();
    }

    @Override
    public Boolean savePayment_voucher_path(String payment_voucher_path) {
        accountPaylBillMapper.savepayment_voucher_path(payment_voucher_path);
        return true;
    }


}
