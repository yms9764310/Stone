package com.jc.service;

import com.jc.model.AccountHandleBill;

import java.util.List;

public interface AccountHandleBillService {
    List<AccountHandleBill> listAccountHandleBill(String page, String limit);
    int countAccountHandleBill();
    Integer saveAccountHandleBill(AccountHandleBill accountHandleBill);
}
