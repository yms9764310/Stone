package com.jc.mapper;

import com.jc.model.AccountHandleBill;
import com.jc.model.AccountReceiveHandleBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountReceiveHandleBillMapeer {
    List<AccountReceiveHandleBill> selectListReceiveHandleBill(@Param("start") Integer start,
                                                  @Param("end") Integer end);
    int countAccountHandleBill();
    //创建应付单
    Integer saveReceiveHandleBill(AccountReceiveHandleBill accountReceiveHandleBill);

}
