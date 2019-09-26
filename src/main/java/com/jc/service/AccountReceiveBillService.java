package com.jc.service;

import com.jc.model.AccountPayBill;
import com.jc.model.AccountReceiveBill;

import java.util.List;

public interface AccountReceiveBillService {
    List<AccountReceiveBill> listAccountReceiveBill(String page, String limit);
    int countAccountReceiveBill();
//    Boolean savePayment_voucher_path(String payment_voucher_path);
}
