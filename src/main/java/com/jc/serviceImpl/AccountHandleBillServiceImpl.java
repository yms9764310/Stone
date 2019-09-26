package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.AccountHandleBillMapper;
import com.jc.model.AccountHandleBill;
import com.jc.model.SysLoginUser;
import com.jc.service.AccountHandleBillService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountHandleBillServiceImpl implements AccountHandleBillService {
    @Autowired
    private AccountHandleBillMapper taccountHandleBillMapper;
    @Override
    public List<AccountHandleBill> listAccountHandleBill(String page, String limit) {
        PageRange paged= new PageRange(page, limit);

        return taccountHandleBillMapper.selectListBill(paged.getStart(),paged.getEnd());
    }

    @Override
    public int countAccountHandleBill() {
        return taccountHandleBillMapper.countAccountHandleBill();
    }

    @Override
    public Integer saveAccountHandleBill(AccountHandleBill accountHandleBill) {
        Date date = new Date();
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();

        return null;
    }
}
