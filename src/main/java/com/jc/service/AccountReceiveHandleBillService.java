package com.jc.service;

import com.jc.model.AccountHandleBill;
import com.jc.model.AccountReceiveHandleBill;

import java.util.List;

public interface AccountReceiveHandleBillService {
    List<AccountReceiveHandleBill> listAccountReceiveHandleBill(String page, String limit);
    int countAccountReceiveHandleBill();
    Integer saveAccountReceiveHandleBill(AccountReceiveHandleBill accountReceiveHandleBill);
}
