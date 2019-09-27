package com.jc.service;

import com.jc.model.AccountHandleBill;
import com.jc.model.AccountPayBill;

import java.util.List;

public interface AccountPayBillService {
    List<AccountPayBill> listAccountPayBill(String page, String limit);
    int countAccountPayBill();
    Boolean savePayment_voucher_path(String payment_voucher_path);
}
