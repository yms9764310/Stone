package com.jc.mapper.sale;/*
 * @author 林锦杰
 * @date 2019/9/26 14:19
 * */

import com.jc.model.sale.SaleBillDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SaleBillDetailMapper {
    List<SaleBillDetail> loadById(Integer sale_bill_id);
    List<SaleBillDetail> loadById1(Integer sale_bill_id);
    SaleBillDetail loadById2(Integer sale_bill_id);
    int deleteSaleBillDetail(Integer sale_bill_id);
}
