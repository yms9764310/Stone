package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.AccountHandleBillMapper;
import com.jc.mapper.AccountPayBillMapper;
import com.jc.model.AccountHandleBill;
import com.jc.model.AccountPayBill;
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
    @Autowired
    private AccountPayBillMapper accountPayBillMapper;
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
    public AccountHandleBill loadAccountHandleBill(Integer id) {
        return taccountHandleBillMapper.loadAccountHandleBill(id);
    }

    @Override
    public Integer updateAccountHandleBill(Integer id) {
        taccountHandleBillMapper.updateAccountHandleBill(id);
        AccountHandleBill accountHandleBill = taccountHandleBillMapper.loadAccountHandleBill(id);
        AccountPayBill accountPayBill = new AccountPayBill();
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int lid = user.getId();
        accountPayBill.setCreator(lid);
        accountPayBill.setCreate_date(new Date());
        accountPayBill.setModifier(lid);
        accountPayBill.setModify_date(new Date());
        accountPayBill.setState("20");
        accountPayBill.setCommit_user_id(accountHandleBill.getCommit_user_id());
        accountPayBill.setSum_money(accountHandleBill.getSum_money());
        accountPayBill.setSource_id(accountHandleBill.getSource_id());
        accountPayBill.setSource_type(accountHandleBill.getSource_type());
        accountPayBill.setPay_date(accountHandleBill.getPay_date());
        accountPayBill.setAccount_no(accountHandleBill.getAccount_no());
        accountPayBill.setEffect_user_id(lid);
        accountPayBillMapper.saveAccountPayBill(accountPayBill);
        return 0;
    }

    @Override
    public Integer saveAccountHandleBill(AccountHandleBill accountHandleBill) {
        Date date = new Date();
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        accountHandleBill.setCreator(id);
        accountHandleBill.setCreate_date(date);
        accountHandleBill.setModifier(id);
        accountHandleBill.setModify_date(date);
        accountHandleBill.setState("3");
        accountHandleBill.setCommit_user_id(id);
        taccountHandleBillMapper.saveAccountHandleBill(accountHandleBill);
        return 1;
    }

}
