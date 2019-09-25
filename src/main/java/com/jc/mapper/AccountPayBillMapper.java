package com.jc.mapper;

import com.jc.model.AccountHandleBill;
import com.jc.model.AccountPayBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountPayBillMapper {
    List<AccountPayBill> selectListPayBill(@Param("start") Integer start,
                                        @Param("end") Integer end);
    int countAccountPayBill();
    //创建应付单
    Integer saveAccountPayBill(AccountPayBill accountPayBill);

    boolean savepayment_voucher_path(String payment_voucher_path);

}
