package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.StoreManagementMapper;
import com.jc.model.*;
import com.jc.service.StoreManagementService;
import com.jc.socket.SocketHandler;
import com.jc.util.ExcelUtils;
import com.jc.utils.ExportUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    // 注入webSocket的处理类
    @Autowired
    private SocketHandler socketHandler;
    @Resource
    private StoreManagementMapper storeManagementMapper;

    Lock l = new ReentrantLock();//创建锁对象

    @Override
    public List<Store> listStoreAll(String page, String limit, String name) {
        PageRange pageRange = new PageRange(page, limit);
        //先获取当前账号的ID,判断是否是主管
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
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
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
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

    public List<LossBeans> countStoreLoss(String startTime, String endTime) {
        List<LossBeans> resultList = new ArrayList<LossBeans>();
        //查询库存量,盘点数量
        List<LossBeans> lossBeans = storeManagementMapper.countStoreLoss(startTime, endTime);
        for (LossBeans lossBean : lossBeans) {
            //获取盘点量
            Double check_number = lossBean.getCheck_number();
            //获取原始数量
            Double stock_number = lossBean.getStock_number();
            //计算损耗量
            Double loss_number = stock_number - check_number;
            lossBean.setLoss_number(loss_number);
            resultList.add(lossBean);
        }
        return resultList;
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
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
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
                socketHandler.sendForOne("确定通知","你的任务已确定",storeCheck.getCheck_user_id());
                return "success";
            }
        }
        return "error";
    }

    //POI导入合并单元格
    public String importExcel(InputStream inputStream, String fileName) throws Exception {

        String message = "Import success";

        boolean isE2007 = false;
        //判断是否是excel2007格式
        if (fileName.endsWith("xlsx")) {
            isE2007 = true;
        }

        int rowIndex = 0;
        try {
            InputStream input = inputStream;  //建立输入流
            Workbook wb;
            //根据文件格式(2003或者2007)来初始化
            if (isE2007) {
                wb = new XSSFWorkbook(input);
            } else {
                wb = new HSSFWorkbook(input);
            }
            Sheet sheet = wb.getSheetAt(0);    //获得第一个表单
            int rowCount = sheet.getLastRowNum() + 1;
            StoreCheck storeCheck = new StoreCheck();
            //查询全部人员
            List<SysUsers> selectdid = storeManagementMapper.listUserAll();
            //查询全部商品
            List<SysPurchaseProduct> selectpid = storeManagementMapper.listProductAll();
            //开始时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date begin_date = null;
            Date end_date = null;
            StoreCheckTaskDetail storeCheckTaskDetail = null;
            List<StoreCheckTaskDetail> storeCheckTaskDetailList = new ArrayList<StoreCheckTaskDetail>();
            for (int i = 1; i < rowCount; i++) {
                rowIndex = i;
                Row row;
                for (int j = 0; j < 6; j++) {
                    if (isMergedRegion(sheet, i, j)) {
                        System.out.print(getMergedRegionValue(sheet, i, j) + "\t");
                        //盘点人
                        for (int d = 0; d < selectdid.size(); d++) {
                            if (String.valueOf(getMergedRegionValue(sheet, 1, 0)).equals(selectdid.get(d).getName())) {
                                storeCheck.setCheck_user_id(selectdid.get(d).getId() + "");
                            }
                        }
//                        storeCheck.setCheck_user_name(getMergedRegionValue(sheet, 1, 0));
                        //开始时间
                        begin_date = sdf.parse(getMergedRegionValue(sheet, 1, 1));
                        storeCheck.setBegin_date(begin_date);
                        //结束时间
                        end_date = sdf.parse(getMergedRegionValue(sheet, 1, 2));
                        storeCheck.setEnd_date(end_date);
                    } else {
                        row = sheet.getRow(i);
                        System.out.print(row.getCell(j) + "\t");
                        switch (j) {
                            case 3:
                                storeCheckTaskDetail = new StoreCheckTaskDetail();
                                for (int d = 0; d < selectpid.size(); d++) {
                                    if (String.valueOf(row.getCell(j)).equals(selectpid.get(d).getName())) {
                                        storeCheckTaskDetail.setProduct_id(selectpid.get(d).getId());
                                    }
                                }
                                break;
                            case 5:
                                storeCheckTaskDetail.setCheck_number(Double.parseDouble(String.valueOf(row.getCell(j))));
                                storeCheckTaskDetailList.add(storeCheckTaskDetail);
                                //添加集合
                                storeCheck.setStoreCheckTaskDetailList(storeCheckTaskDetailList);
                                break;
                        }
                    }

                }
                System.out.print("\n");
            }
            SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
            String id = String.valueOf(user.getId());
            Date date = new Date();//获取时间
            StoreCheck storeCheck1 = new StoreCheck(id, date,
                    storeCheck.getCheck_user_id(), "3", storeCheck.getBegin_date(), storeCheck.getEnd_date());
            storeManagementMapper.insertCheckTask(storeCheck1);
            for (StoreCheckTaskDetail checkTaskDetail : storeCheckTaskDetailList) {
                checkTaskDetail.setCheck_id(storeCheck1.getId());
                storeManagementMapper.insertCheckTaskDetailPoi(checkTaskDetail);
            }
            List<StoreManagementBeans> storeManagementBeans = storeManagementMapper.listStoreManagementSupervisor();
            for (StoreManagementBeans storeManagementBean : storeManagementBeans) {
                String user_id = String.valueOf(storeManagementBean.getId());
                socketHandler.sendForOne("待审核通知","有工作待审核",user_id);
            }

        } catch (Exception ex) {
            message = "Import failed, please check the data in " + rowIndex + " rows ";
        }
        return message;
    }


    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell) {
        if (cell == null) return "";
        return cell.getStringCellValue();
    }


    /**
     * 合并单元格处理,获取合并行
     *
     * @param sheet
     * @return List<CellRangeAddress>
     */
    public List<CellRangeAddress> getCombineCell(Sheet sheet) {
        List<CellRangeAddress> list = new ArrayList<>();
        //获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        //遍历所有的合并单元格
        for (int i = 0; i < sheetmergerCount; i++) {
            //获得合并单元格保存进list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }

    private int getRowNum(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet) {
        int xr = 0;
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        for (CellRangeAddress ca : listCombineCell) {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
                if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
                    xr = lastR;
                }
            }

        }
        return xr;

    }

    /**
     * 判断单元格是否为合并单元格，是的话则将单元格的值返回
     *
     * @param listCombineCell 存放合并单元格的list
     * @param cell            需要判断的单元格
     * @param sheet           sheet
     * @return
     */
    public String isCombineCell(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet)
            throws Exception {
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        String cellValue = null;
        for (CellRangeAddress ca : listCombineCell) {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
                if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
                    Row fRow = sheet.getRow(firstR);
                    Cell fCell = fRow.getCell(firstC);
                    cellValue = getCellValue(fCell);
                    break;
                }
            } else {
                cellValue = "";
            }
        }
        return cellValue;
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }

        return null;
    }


    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    private boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public String SureCountingTask(StoreCheck storeCheck) {
        //先获取当前账号的ID,判断是否是主管
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
            List<StoreCheckTaskDetail> storeCheckTaskDetailList = storeCheck.getStoreCheckTaskDetailList();
            for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheckTaskDetailList) {
                storeManagementMapper.SureCountingTask(storeCheckTaskDetail);
            }
            StoreCheck newstoreCheck = new StoreCheck(storeCheck.getId(), "3");
            storeManagementMapper.updateState(newstoreCheck);

            return "success";
        }
        return "error";
    }

    public String ReviewCountingTask(StoreCheck storeCheck) {
        //先获取当前账号的ID,判断是否是主管
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
            List<StoreCheckTaskDetail> storeCheckTaskDetailList = storeCheck.getStoreCheckTaskDetailList();
            for (StoreCheckTaskDetail storeCheckTaskDetail : storeCheckTaskDetailList) {
                int product_id = storeCheckTaskDetail.getProduct_id();
                Double number = storeCheckTaskDetail.getCheck_number();
                int check_id = storeCheckTaskDetail.getCheck_id();
                Double stock_number = storeCheckTaskDetail.getCheck_number();
                StoreCheckTaskDetail newStoreCheckTaskDetail = new StoreCheckTaskDetail(stock_number,product_id,check_id);
                Store newStore = new Store(product_id, number);
                storeManagementMapper.updateStoreNumber(newStore);
                storeManagementMapper.updateDetailStoreNumber(newStoreCheckTaskDetail);
            }
            StoreCheck newstoreCheck = new StoreCheck(storeCheck.getId(), "6");
            storeManagementMapper.reviewState(newstoreCheck);
            socketHandler.sendForOne("审核通知","你的请求已通过审核",storeCheck.getCheck_user_id());

            return "success";
        }
        return "error";
    }

    @Override
    public String updateCheckState(StoreCheck storeCheck) {
        //先获取当前账号的ID,判断是否是主管
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
        SysUsersBeans sysUsersBeans = storeManagementMapper.loadById(id);
        if (sysUsersBeans.getName().equals("主管")) {
            //判断是否是仓库管理
            if (sysUsersBeans.getDepart_id().equals("仓库管理")) {
                storeManagementMapper.updateState(storeCheck);
                socketHandler.sendForOne("驳回通知","你的请求被驳回",storeCheck.getCheck_user_id());
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
        SysLoginUser user = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
        int id = user.getId();
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
    public String exportExcel(String[] titles, ServletOutputStream outputStream, Integer check_id) {

//      该类就不贴出代码了，建议自己按照图示，拼装所需数据集合
        List<StoreBeans> resultData = new ArrayList<StoreBeans>();
        List<StoreCheckTaskDetail> list = storeManagementMapper.listByCheckId(check_id);
        StoreBeans storeBeans1 = null;
        for (StoreCheckTaskDetail storeCheckTaskDetail : list) {
            storeBeans1 = new StoreBeans();
            //获取商品名称
            String product_name = storeCheckTaskDetail.getName();
            storeBeans1.setProduct_name(product_name);
            //获取盘点数量
            Double check_number = storeCheckTaskDetail.getCheck_number();
            storeBeans1.setCheck_number(0.0);
            //获取库存量
            int count = storeManagementMapper.count(storeCheckTaskDetail.getProduct_id());
            storeBeans1.setNumber(Double.valueOf(count));
            resultData.add(storeBeans1);
        }
        int id = check_id;
        StoreCheck storeCheck = storeManagementMapper.loadBy_id(id);
        //获取盘点人名字
        storeBeans1.setCheck_user_name(storeCheck.getCheck_user_name());
        //获取开始时间
        storeBeans1.setBegin_date(storeCheck.getBegin_date());
        //获取结束时间
        storeBeans1.setEnd_date(storeCheck.getEnd_date());
        //resultData.add(storeBeans1);
        // 创建一个workbook 对应一个excel应用文件
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = workBook.createSheet("导出excel例子");
        // 自适应列宽度
        sheet.autoSizeColumn(1, true);
        ExportUtil exportUtil = new ExportUtil(workBook, sheet);
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();

        // 构建表头
        XSSFRow headRow = sheet.createRow(0);
        XSSFCell cell = null;
        for (int i = 0; i < titles.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titles[i]);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取盘点人
        String check_user_name = storeBeans1.getCheck_user_name();
        //获取开始时间
        String begin_date = sdf.format(storeBeans1.getBegin_date());
        //获取结束时间
        String end_date = sdf.format(storeBeans1.getEnd_date());
        // 构建表体数据
        if (resultData != null && resultData.size() > 0) {
            for (int j = 0; j < resultData.size(); j++) {
                XSSFRow bodyRow = sheet.createRow(j + 1);
                StoreBeans storeBeans = resultData.get(j);

                cell = bodyRow.createCell(0);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(check_user_name);

                cell = bodyRow.createCell(1);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(begin_date);

                cell = bodyRow.createCell(2);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(end_date);

                cell = bodyRow.createCell(3);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(storeBeans.getProduct_name());

                cell = bodyRow.createCell(4);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(storeBeans.getNumber());

                cell = bodyRow.createCell(5);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(storeBeans.getCheck_number());

            }
//                /*
//                 * 设定合并单元格区域范围
//                 *  firstRow  0-based
//                 *  lastRow   0-based
//                 *  firstCol  0-based
//                 *  lastCol   0-based
//                 */
//            sheet.addMergedRegion(new CellRangeAddress(2, 7, 0, 0));
//            sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
//            sheet.addMergedRegion(new CellRangeAddress(4, 7, 1, 1));

            int lastRowNum = sheet.getLastRowNum();
            exportUtil.mergerData(sheet, 0, 1, lastRowNum);
            Map<String, String> map = exportUtil.mergerData(sheet, 1, 1, lastRowNum);
//            exportUtil.mergerData(sheet, 2, 1 , lastRowNum);
//            Map<String,String> map2 = exportUtil.mergerData(sheet, 2, 1 , lastRowNum);
//            System.out.println("map: "+JSON.toJSONString(map));
            for (String value : map.values()) {
                String[] strArr = value.split(",");
                int firstRow = Integer.parseInt(strArr[0]) - 1;
                //定义最后一列要合并的单元格    若需要最后一行空出来  需要进行-1操作
                int lastRow = Integer.parseInt(strArr[1]);
                exportUtil.mergerData(sheet, 2, firstRow, lastRow);
            }
//            for(String value : map2.values()){
//                String [] strArr = value.split(",");
//                int firstRow = Integer.parseInt(strArr[0])-1;
//                int lastRow = Integer.parseInt(strArr[1])-1;
//                exportUtil.mergerData(sheet, 4, firstRow, lastRow);
//            }

        }
        FileOutputStream out = null;
        File dir = new File("E://Temp44");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            out = new FileOutputStream(new File(dir, "3.xlsx"));
            workBook.write(out);
            out.flush();
            out.close();
            socketHandler.sendForOne("工作分配通知","有工作待完成",storeCheck.getCheck_user_id());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "success";
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
