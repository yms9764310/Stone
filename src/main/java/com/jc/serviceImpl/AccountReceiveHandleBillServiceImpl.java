package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.AccountReceiveHandleBillMapeer;
import com.jc.model.AccountHandleBill;
import com.jc.model.AccountReceiveHandleBill;
import com.jc.model.SysLoginUser;
import com.jc.service.AccountReceiveHandleBillService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        Date date = new Date();
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        accountReceiveHandleBill.setCreator(id);
        accountReceiveHandleBill.setCreate_date(date);
        accountReceiveHandleBill.setModifier(id);
        accountReceiveHandleBill.setModify_date(date);
        accountReceiveHandleBill.setState("3");
        accountReceiveHandleBill.setCommit_user_id(id);
        accountReceiveHandleBillMapeer.saveAccountReceiveHandleBill(accountReceiveHandleBill);
        return 1;
    }

    @Override
    public AccountHandleBill loadAccountReceiveHandleBill(Integer id) {
        return accountReceiveHandleBillMapeer.loadAccountReceiveHandleBill(id);
    }

    @Override
    public Integer updateAccountReceiveHandleBill(Integer id) {
        return null;
    }
}
