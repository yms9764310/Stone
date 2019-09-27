package com.jc.service;

import com.jc.model.AccountHandleBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountHandleBillService {
    List<AccountHandleBill> listAccountHandleBill(String page, String limit);
    int countAccountHandleBill();
    Integer saveAccountHandleBill(AccountHandleBill accountHandleBill);
    AccountHandleBill loadAccountHandleBill( Integer id);
    Integer updateAccountHandleBill(Integer id);

}
