package com.jc.mapper;

import com.jc.model.AccountPayBill;
import com.jc.model.AccountReceiveBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountReceiveBillMapper {
    List<AccountReceiveBill> selectListAccountReceiveBill(@Param("start") Integer start,
                                               @Param("end") Integer end);
    int countAccountReceiveBill();
    //创建应付单
    Integer saveAccountReceiveBill(AccountReceiveBill accountReceiveBill);

    boolean savepayment_voucher_path(String payment_voucher_path);

}
