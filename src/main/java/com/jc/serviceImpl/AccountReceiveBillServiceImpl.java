package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.AccountReceiveBillMapper;
import com.jc.model.AccountPayBill;
import com.jc.model.AccountReceiveBill;
import com.jc.service.AccountPayBillService;
import com.jc.service.AccountReceiveBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountReceiveBillServiceImpl implements AccountReceiveBillService {
    @Autowired
    private AccountReceiveBillMapper accountReceiveBillMapper;

    @Override
    public List<AccountReceiveBill> listAccountReceiveBill(String page, String limit) {
        PageRange pageRange = new PageRange(page,limit);
        return accountReceiveBillMapper.selectListAccountReceiveBill(pageRange.getStart(),pageRange.getEnd());
    }

    @Override
    public int countAccountReceiveBill() {
        return 0;
    }


}
