package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.sale.SaleBillMapper;
import com.jc.mapper.sale.SaleCustomerMapper;
import com.jc.mapper.SysLoginUserMapper;
import com.jc.model.sale.SaleBill;
import com.jc.model.sale.SaleCustomer;
import com.jc.model.SysLoginUser;
import com.jc.service.sale.SaleCustomerService;
import com.jc.model.sale.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@Transactional
public class SaleCustomerServiceImpl implements SaleCustomerService {
    @Resource
    private SaleCustomerMapper saleCustomerMapper;
    @Resource
    private SaleBillMapper saleBillMapper;
    @Resource
    private SysLoginUserMapper sysLoginUserMapper;
    @Override
    public List<SaleCustomer> listCustomer(String page, String limit, String name, String address) {
        PageRange pageRange = new PageRange(page, limit);
        return saleCustomerMapper.listCustomer(pageRange.getStart(),pageRange.getEnd(),name,address);
    }

    @Override
    public int countGetAll() {
        return saleCustomerMapper.countGetAll();
    }

    @Override
    public String insertSingleCustomer(SaleCustomer saleCustomer) {
        saleCustomerMapper.insertSingleCustomer(saleCustomer);
        return "success";
    }

    @Override
    public int deleteCustomer(Integer id) {

        saleCustomerMapper.deleteCustomer(id);
        return id;
    }

    @Override
    public SaleCustomer loadById(int id) {
        return saleCustomerMapper.loadById(id);
    }

    @Override
    public List<SaleCustomer> loadById1(int id) {
        return saleCustomerMapper.loadById1(id);
    }

    @Override
    public String updateCustomer(SaleCustomer saleCustomer) {
        saleCustomerMapper.updateCustomer(saleCustomer);
        return "success";
    }

    @Override
    public boolean insertCustomerExcel(SaleCustomer saleCustomer) {
        return saleCustomerMapper.insertCustomerExcel(saleCustomer);
    }

    @Override
    public String ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);

        MultipartFile file = multipartRequest.getFile("upfile");
        if(file.isEmpty()){
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream in =null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            SaleCustomer saleCustomer = new SaleCustomer();
            int id = 2;
            SysLoginUser selectById = sysLoginUserMapper.loadById(id);
            //vo.setUserId(Integer.valueOf(String.valueOf(lo.get(0))));  // 刚开始写了主键，由于主键是自增的，又去掉了，现在只有批量插入的功能，不能对现有数据进行修改了
            saleCustomer.setCreator(selectById.getCreator()+"");     // 表格的第一列   注意数据格式需要对应实体类属性
            //由于数据库中此字段是datetime，所以要将字符串时间格式：yyyy-MM-dd HH:mm:ss，转为Date类型
            saleCustomer.setCreate_date(new Date());
            //表格第三列 修改人
            saleCustomer.setModifier(selectById.getModifier()+"");
            saleCustomer.setModify_date(new Date());
            //表格第五列 状态
            saleCustomer.setState("draft");
            //表格第六列 姓名
            saleCustomer.setName(String.valueOf(lo.get(0)));
            //表格第七列 地址
            saleCustomer.setAddress(String.valueOf(lo.get(1)));
            //表格第八列 联系方式
            saleCustomer.setPhone(String.valueOf(lo.get(2)));
            //表格第九列 所属机构-公司
            saleCustomer.setCompany(String.valueOf(lo.get(3)));
            System.out.println("从excel中读取的实体类对象："+ saleCustomer);
            saleCustomerMapper.insertCustomerExcel(saleCustomer);
        }
        System.out.println("文件导入成功！");
        return "success";
    }

   /* @Override
    public SaleCustomer loadByCusId(Integer id) {
        return saleCustomerMapper.loadByCusId(id);
    }*/

    @Override
    public List<SaleCustomer> listChooseCustomer() {
        return saleCustomerMapper.listChooseCustomer();
    }
}
