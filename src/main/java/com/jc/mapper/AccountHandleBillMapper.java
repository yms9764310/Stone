package com.jc.mapper;

import com.jc.model.AccountHandleBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountHandleBillMapper {
    List<AccountHandleBill> selectListBill(@Param("start") Integer start,
                                           @Param("end") Integer end);
    int countAccountHandleBill();
    //创建应付单
    Integer saveAccountHandleBill(AccountHandleBill accountHandleBill);
}
