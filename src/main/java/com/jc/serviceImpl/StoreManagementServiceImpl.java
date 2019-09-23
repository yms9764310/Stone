package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.StoreManagementMapper;
import com.jc.model.*;
import com.jc.service.StoreManagementService;
import com.jc.util.ExcelUtils;
import com.jc.utils.ExportDataToExcle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 年: 2019
 * 月: 08
 * 日: 15
 * 小时: 17
 * 分钟: 42
 *
 * @author 严脱兔
 */

@Service
@Transactional
public class StoreManagementServiceImpl implements StoreManagementService {
    @Resource
    private StoreManagementMapper storeManagementMapper;

    Lock l = new ReentrantLock();//创建锁对象

    @Override
    public List<Store> listStoreAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        //先获取当前账号的ID,判断是否是主管
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                List<Store> stores = storeManagementMapper.listStoreAll(pageRange.getStart(), pageRange.getEnd(), name);
                return stores;
            }
        }
        return null;
    }

    //获取锁定量 待发货量  库存总数
    public StoreCheckOut loadStoreCheckOut(Integer product_id) {
        StoreCheckOut storeCheckOut = new StoreCheckOut();
        //获取库存总数
        Double countnumber = storeManagementMapper.CheckOutCount_number(product_id);
        //获取锁定量
        Double locking_number = storeManagementMapper.CheckOutLocking_number(product_id);
        if (locking_number == null) {
            locking_number = 0.0;
        }
        storeCheckOut.setLocking_number(locking_number);
        //获取待发货量
        Double delivered_number = storeManagementMapper.CheckOutDelivered_number(product_id);
        //计算可用数量
        if (delivered_number == null) {
            delivered_number = 0.0;
        }
        storeCheckOut.setDelivered_number(delivered_number);
        Double can_number = (countnumber - locking_number - delivered_number);
        storeCheckOut.setCan_number(can_number);
        return storeCheckOut;
    }


    @Override
    public List<StoreCheck> listCheckAll(String page, String limit, String startTime, String endTime) {
        PageRange pageRange = new PageRange(page, limit);
        //判断是否是仓库管理人员
        //先获取当前账号的ID,判断是否是主管
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
            if (sysUsersBeans.getName().equals("主管")) {
                //判断是否是仓库管理
                List<StoreCheck> storeChecks = storeManagementMapper.listCheckAll(pageRange.getStart(), pageRange.getEnd(), startTime, endTime);
                return storeChecks;
            } else {
                String name = sysUsersBeans.getUser_name();
                List<StoreCheck> storeChecks = storeManagementMapper.listCheckWith(pageRange.getStart(), pageRange.getEnd(), startTime, endTime, name);
                return storeChecks;
            }
        }
        return null;
    }

    @Override
    public int countGetAll() {
        return storeManagementMapper.countGetAll();
    }

    @Override
    public int countGetCheckAll() {
        return storeManagementMapper.countGetCheckAll();
    }

    public List<Store> countStoreLoss() {
        //查询库存量
        List<Store> stores = storeManagementMapper.countStoreLoss();
        //审核通过后，修改库存量时，AOP切进方法  记录修改时间与修改的数量，然后传到前端
        // AOP切
        return stores;
    }

    @Override
    public int countGetCheckOutAll() {
        return storeManagementMapper.countGetCheckOutAll();
    }

    @Override
    public int countGetPutInAll() {
        return storeManagementMapper.countGetPutInAll();
    }

    public int deleteCheckTask(Integer id) {
        List<StoreCheckTaskDetail> storeCheckTaskDetails = storeManagementMapper.listByCheckId(id);
        for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheckTaskDetails) {
            storeManagementMapper.deleteCheckTaskDetail(storeCheckTaskDetail.getCheck_id());
        }
        storeManagementMapper.deleteCheckTask(id);
        return id;
    }

    @Override
    public String insertCountingTask(StoreCheck storeCheck) {
        Date date = new Date();//获取时间
        StoreCheck storeCheck1 = new StoreCheck("3", date,
                storeCheck.getCheck_user_id(), "3", storeCheck.getBegin_date(), storeCheck.getEnd_date());
        storeManagementMapper.insertCheckTask(storeCheck1);
        for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheck.getStoreCheckTaskDetailList()) {
            storeCheckTaskDetail.setCheck_id(storeCheck1.getId());
            storeManagementMapper.insertCheckTaskDetail(storeCheckTaskDetail);
        }

        return "success";
    }

    @Override
    public Store loadByProductId(String product_id) {
        return storeManagementMapper.loadByProductId(product_id);
    }

    @Override
    public SysUsersBeans loadById(Integer id) {
        return storeManagementMapper.loadById(id);
    }

    @Override
    public StoreCheck loadByCheck_Id(Integer id) {
        StoreCheck storeCheck = storeManagementMapper.loadByCheck_Id(id);
        List<StoreCheckTaskDetail> storeCheckTaskDetails = storeManagementMapper.listByCheckId(id);
        storeCheck.setStoreCheckTaskDetailList(storeCheckTaskDetails);
        for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheckTaskDetails) {
            int product_id = storeCheckTaskDetail.getProduct_id();
            int number = storeManagementMapper.count(product_id);
            storeCheckTaskDetail.setNumber(number);
            storeCheck.setStoreCheckTaskDetailList(storeCheckTaskDetails);
        }
        return storeCheck;
    }

    @Override
    public String updateCountingTask(StoreCheck storeCheck) {
        //先获取当前账号的ID,判断是否是主管
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                Date date = new Date();//获取时间
                StoreCheck storeCheck1 = new StoreCheck("3", date, storeCheck.getCheck_user_id(),
                        storeCheck.getBegin_date(), "5", storeCheck.getEnd_date(), storeCheck.getId());
                storeManagementMapper.updateCountingTask(storeCheck1);
                storeManagementMapper.deleteCheckTaskDetail(storeCheck1.getId());
                List<StoreCheckTaskDetail> storeCheckTaskDetail = storeCheck.getStoreCheckTaskDetailList();
                for (StoreCheckTaskDetail checkTaskDetail : storeCheckTaskDetail) {
                    StoreCheckTaskDetail storeCheckTaskDetail1 = new StoreCheckTaskDetail();
                    storeCheckTaskDetail1.setCheck_id(storeCheck1.getId());
                    storeCheckTaskDetail1.setProduct_id(checkTaskDetail.getValue());
                    storeManagementMapper.updateCountingTaskDetail(storeCheckTaskDetail1);
                }
                return "success";
            }
        }
        return "error";
    }


    //导入盘点结果单
    //合并单元格问题。
    public String ajaxUploadExcel(HttpServletRequest request,
                                  HttpServletResponse response) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);//ajax提交，做文件上传时，由于上传的文件是另一台服务器
        //读取文件
        MultipartFile file = multipartRequest.getFile("upfile");
        //判断文件是否存在
        if (file.isEmpty()) {
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream in =null; //读取数据
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建集合
        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtils().getBankListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            StoreCheck storeCheck = new StoreCheck();
            /*这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            User j = null;
			try {
				j = photoUserTestMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("没有新增");
			}*/
            //vo.setUserId(Integer.valueOf(String.valueOf(lo.get(0))));  // 刚开始写了主键，由于主键是自增的，又去掉了，现在只有批量插入的功能，不能对现有数据进行修改了
//            storeCheck.setUname(String.valueOf(lo.get(0)));     // 表格的第一列   注意数据格式需要对应实体类属性
//            //性别
//            if (String.valueOf(lo.get(1)).equals("男")) {
//                storeCheck.setSex(33 + "");
//            } else if (String.valueOf(lo.get(1)).equals("女")) {
//                storeCheck.setSex(34 + "");
//            }
            // vo.setSex(String.valueOf(lo.get(1)));   // 表格的第二列
            //盘点人
            List<SysUsers> selectdid = storeManagementMapper.listUserAll();
            for (int d = 0; d < selectdid.size(); d++) {
                if (String.valueOf(lo.get(0)).equals(selectdid.get(d).getName())) {
                    storeCheck.setCheck_user_id(selectdid.get(d).getId() + "");
                }
            }
            //vo.setDepartmentId(String.valueOf(lo.get(2)));   // 表格的第三列
            //开始时间
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date begin_date = null;
            try {
                begin_date = sdf.parse((String) lo.get(1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            storeCheck.setBegin_date(begin_date);

            //结束时间
            Date end_date = null;
            try {
                end_date = sdf.parse((String) lo.get(2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            storeCheck.setEnd_date(end_date);
            //vo.setClassOfPosition(String.valueOf(lo.get(3)));   // 表格的第四列
            //商品名称
            StoreCheckTaskDetail storeCheckTaskDetail = new StoreCheckTaskDetail();
            List<SysPurchaseProduct> selectpid = storeManagementMapper.listProductAll();
            for (int d = 0; d < selectpid.size(); d++) {
                if (String.valueOf(lo.get(3)).equals(selectpid.get(d).getName())) {
                    storeCheckTaskDetail.setProduct_id(selectpid.get(d).getId());
                }
            }

            //vo.setPosition(String.valueOf(lo.get(4)));   // 表格的第五列
            //库存数量
                  Store store = new Store();
            List<Store> selectpnum = storeManagementMapper.listStoreNumber();
            System.out.println(lo.get(4));
            for (int d = 0; d < selectpnum.size(); d++) {
                if (String.valueOf(lo.get(4)).equals(String.valueOf(selectpnum.get(d).getNumber()))) {
                    store.setNumber(selectpnum.get(d).getNumber());
                }
            }
            // vo.setGradeOfJudge(String.valueOf(lo.get(5)));   // 表格的第六列
            //盘点数量
            System.out.println(lo.get(5));
            System.out.println(Double.parseDouble((String)lo.get(5)));
            storeCheckTaskDetail.setCheck_number(Double.parseDouble((String)lo.get(5)));
            // vo.setCategory(String.valueOf(lo.get(6)));   // 表格的第七列
            //vo.setRegTime(Date.valueOf(String.valueOf(lo.get(2))));
            Date date = new Date();//获取时间
            StoreCheck storeCheck1 = new StoreCheck("3", date,
                    storeCheck.getCheck_user_id(), "3", storeCheck.getBegin_date(), storeCheck.getEnd_date());
            storeManagementMapper.insertCheckTask(storeCheck1);
			/*if(j == null)
			{
		            photoUserTestMapper.insert(vo);
			}
			else
			{
		            photoUserTestMapper.updateByPrimaryKey(vo);
			}*/
        }
        System.out.println("文件导入成功！");
        return "success";
    }

    @Override
    public String SureCountingTask(StoreCheck storeCheck) {
        //先获取当前账号的ID,判断是否是主管
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
            List<StoreCheckTaskDetail> storeCheckTaskDetailList = storeCheck.getStoreCheckTaskDetailList();
            for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheckTaskDetailList) {
                storeManagementMapper.SureCountingTask(storeCheckTaskDetail);
            }
            StoreCheck newstoreCheck = new StoreCheck(storeCheck.getId(),"3");
            storeManagementMapper.updateState(newstoreCheck);

            return "success";
        }
        return "error";
    }

    public String ReviewCountingTask(StoreCheck storeCheck) {
        //先获取当前账号的ID,判断是否是主管
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
            List<StoreCheckTaskDetail> storeCheckTaskDetailList = storeCheck.getStoreCheckTaskDetailList();
            for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheckTaskDetailList) {
                int product_id = storeCheckTaskDetail.getProduct_id();
                Double number = storeCheckTaskDetail.getCheck_number();
                Store newstore = new Store(product_id,number);
                storeManagementMapper.updateStoreNumber(newstore);
            }
            StoreCheck newstoreCheck = new StoreCheck(storeCheck.getId(),"6");
            storeManagementMapper.updateState(newstoreCheck);

            return "success";
        }
        return "error";
    }

    @Override
    public String updateCheckState(StoreCheck storeCheck) {
        //先获取当前账号的ID,判断是否是主管
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                storeManagementMapper.updateState(storeCheck);
                return "success";
            }
        }
        return "error";
    }


    public StoreWarn loadByProduct_id(Integer product_id) {
        StoreWarn storeWarn = storeManagementMapper.loadByProduct_id(product_id);
        return storeWarn;
    }

    @Override
    public String updateWarn(StoreWarn storeWarn) {
        l.lock();//加锁
        try {
            //先获取当前账号的ID,判断是否是主管
            int id = 3;
            SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
            if (sysUsersBeans.getName().equals("主管")) {
                //判断是否是仓库管理
                if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                    storeManagementMapper.updateWarn(storeWarn);
                    return "success";
                }
            }
        } finally {
            l.unlock(); // 解锁
        }
        return "error";

    }

    public List<SysUsers> listUsers() {
        //根据主管账号查询当前部门下的人员
        int id = 3;
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                //获取本部门人员
                String department_id = sysUsersBeans.getDepartment_id();
                return storeManagementMapper.listUsers(department_id);
            }
        }
        return null;
    }

    @Override
    public List<StoreCheckOut> listCheckOut(String page, String limit, String startTime, String endTime, String
            name, String source_kind) {
        PageRange pageRange = new PageRange(page, limit);
        List<StoreCheckOut> storeCheckOuts = storeManagementMapper.listCheckOut(pageRange.getStart(), pageRange.getEnd(), startTime, endTime, name, source_kind);
        return storeCheckOuts;
    }

    @Override
    public List<StorePutIn> listPutIn(String page, String limit, String startTime, String endTime, String
            name, String source_type) {
        PageRange pageRange = new PageRange(page, limit);
        List<StorePutIn> storePutIns = storeManagementMapper.listPutIn(pageRange.getStart(), pageRange.getEnd(), startTime, endTime, name, source_type);
        return storePutIns;
    }
    //导出盘点任务
    //合并单元格问题。
    public HSSFWorkbook exportExcelInfo(Integer check_id) throws
            InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException {
        List<StoreBeans> resultData = new ArrayList<StoreBeans>();
        ProductNumberBeans productNumberBeans = new ProductNumberBeans();
        List<ProductNumberBeans> productNumberBeansList = new ArrayList<ProductNumberBeans>();
        List<StoreCheckTaskDetail> list = storeManagementMapper.listByCheckId(check_id);
        StoreBeans storeBeans1 = new StoreBeans();
        for (StoreCheckTaskDetail storeCheckTaskDetail : list) {
            productNumberBeans.setProduct_name(storeCheckTaskDetail.getName());
            productNumberBeans.setCheck_number(storeCheckTaskDetail.getCheck_number());
            int count = storeManagementMapper.count(storeCheckTaskDetail.getProduct_id());
            productNumberBeans.setNumber(Double.valueOf(count));
            productNumberBeansList.add(productNumberBeans);
        }
        storeBeans1.setProductNumberBeansList(productNumberBeansList);
        int id = check_id;
        StoreCheck storeCheck = storeManagementMapper.loadBy_id(id);
        storeBeans1.setCheck_user_name(storeCheck.getCheck_user_name());
        storeBeans1.setBegin_date(storeCheck.getBegin_date());
        storeBeans1.setEnd_date(storeCheck.getEnd_date());
        resultData.add(storeBeans1);
        HSSFWorkbook xssfWorkbook = null;
        //调用ExcelUtil的方法
        try {
            xssfWorkbook = ExportDataToExcle.export(resultData, "E:\\Temp\\盘点任务单.xls", "com.jc.model.StoreBeans");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xssfWorkbook;
    }

    public List<ToDoList> listLimitData(String page, String limit, List<ToDoList> limitData) {
        List<ToDoList> resultData = new ArrayList<ToDoList>();
        //起始下标
        int fromIndex = Integer.valueOf(limit) * (Integer.valueOf(page) - 1);
        //终止下标
        int intLimit = Integer.valueOf(limit);
        int toIndex = fromIndex + intLimit;
        int size = limitData.size();
        if (toIndex >= size) {
            toIndex = size;
        }
        resultData = limitData.subList(fromIndex, toIndex);
        return resultData;
    }
}
