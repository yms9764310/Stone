package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.AccountReceiveHandleBillMapeer;
import com.jc.model.AccountReceiveHandleBill;
import com.jc.service.AccountReceiveHandleBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountReceiveHandleBillServiceImpl implements AccountReceiveHandleBillService {
@Autowired
    AccountReceiveHandleBillMapeer accountReceiveHandleBillMapeer;
    @Override
    public List<AccountReceiveHandleBill> listAccountReceiveHandleBill(String page, String limit) {
        PageRange pageRange = new PageRange(page,limit);
        return accountReceiveHandleBillMapeer.selectListReceiveHandleBill(pageRange.getStart(),pageRange.getEnd());
    }

    @Override
    public int countAccountReceiveHandleBill() {
        return accountReceiveHandleBillMapeer.countAccountReceiveHandleBill();
    }

    @Override
    public Integer saveAccountReceiveHandleBill(AccountReceiveHandleBill accountReceiveHandleBill) {
        return null;
    }
}
