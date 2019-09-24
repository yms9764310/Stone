package com.jc.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaccountHandleBillMapper {
    List<TaccountHandleBillMapper> selectListBill(@Param("start") Integer start,
                                                  @Param("end") Integer end);
}
