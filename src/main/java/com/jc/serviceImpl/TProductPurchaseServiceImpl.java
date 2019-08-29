package com.jc.serviceImpl;

import com.jc.beans.response.PageRange;
import com.jc.mapper.TProductPurchaseMapper;
import com.jc.model.TProductsyspurchaseproduct;
import com.jc.service.TProductPurchaseService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class TProductPurchaseServiceImpl implements TProductPurchaseService {
    @Resource
    TProductPurchaseMapper tProductPurchaseDao;
    @Override
    public int saveT_Product_sys_purchase_product(TProductsyspurchaseproduct tProductSysPurchaseProduct) {
        return tProductPurchaseDao.saveT_Product_sys_purchase_product( tProductSysPurchaseProduct );
    }

    @Override
    public int removeT_Product_sys_purchase_product(int id) {
        return tProductPurchaseDao.removeT_Product_sys_purchase_product( id );
    }

    @Override
    public int updateT_Product_sys_purchase_product(TProductsyspurchaseproduct tProductSysPurchaseProduct) {
        return tProductPurchaseDao.updateT_Product_sys_purchase_product( tProductSysPurchaseProduct );
    }

    @Override
    public List<TProductsyspurchaseproduct> listT_Product_sys_purchase_product(String page, String limit, String name, String kind) {
        PageRange pageRange = new PageRange(page, limit);
        return tProductPurchaseDao.listT_Product_sys_purchase_product(pageRange.getStart(),pageRange.getEnd(),name,kind );
    }

    @Override
    public int countT_Product_sys_purchase_product(String name, String kind) {
        return tProductPurchaseDao.countT_Product_sys_purchase_product( name,kind );
    }

    @Override
    public TProductsyspurchaseproduct loadT_Product_sys_purchase_product(int id) {
        TProductsyspurchaseproduct tProductsyspurchaseproduct = tProductPurchaseDao.loadT_Product_sys_purchase_product( id );
//        int user_id  = tProductsyspurchaseproduct.getCreator();
//        if (){
//            return tProductsyspurchaseproduct;
//        }else{
//            return tProductsyspurchaseproduct;
//        }
        return tProductsyspurchaseproduct;
    }
        private static final String XLS = "xls";
        private static final String XLSK = "xlsx";//定义全局的常量值

    @SuppressWarnings("resource")
    public Map<String, Object> importExcel(MultipartFile file) throws Exception {
        List<TProductsyspurchaseproduct> list = new ArrayList<TProductsyspurchaseproduct>();
        Map<String, Object> rsultMap = new HashMap<String, Object>();


        Workbook workbook = null;
        String fileName = file.getOriginalFilename();
        if(fileName.endsWith(XLS)) {
            //2003
            try {
                workbook = new HSSFWorkbook(file.getInputStream());
            } catch (Exception e) {
                e.printStackTrace( );
            }

        }else if(fileName.endsWith(XLSK)) {
            try {
                //2007
                workbook = new XSSFWorkbook(file.getInputStream());
            } catch (Exception e) {
                e.printStackTrace( );
            }
        }else {
            throw new Exception("文件不是Excel文件");
        }
        Sheet sheet = workbook.getSheet("Sheet1");
        int rows = sheet.getLastRowNum();//指定行数。一共多少+
        if(rows==0) {
            throw new Exception("请填写行数");
        }

        for (int i = 1; i < rows+1; i++) {
            //读取左上端单元格
            Row row = sheet.getRow(i);
            //行不为空
            if(row != null) {
                //读取cell
                TProductsyspurchaseproduct tblFixChange = new TProductsyspurchaseproduct();
                //商品名称
                String name = getCellValue(row.getCell(0));
                tblFixChange.setName(name);
                //商品类别
                String kind = getCellValue(row.getCell(1));
                tblFixChange.setKind( kind );
                //商品类型
                String model_type = getCellValue(row.getCell(2));
               tblFixChange.setModel_type( model_type );
                //商品规格
                String standard = getCellValue(row.getCell(3));
                tblFixChange.setModel_type( standard );
                //商品描述
                String description = getCellValue(row.getCell(3));
                tblFixChange.setModel_type( description );
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String time = sdf.format(new Date());
//				tblFixChange.setCreateTime(time);
//				tblFixChange.setModifyTime(time);
                tblFixChange.setCreator(1);
                Date date = new Date(  );
                tblFixChange.setModify_date(date );
                tblFixChange.setModifier( 1 );
                tblFixChange.setState( "1" );
                tblFixChange.setCreate_date( date );
                tProductPurchaseDao.saveT_Product_sys_purchase_product( tblFixChange );
//                list.add(tblFixChange);//把实数据放入集合里
            }
        }
        try {
//            tProductPurchaseDao.addBatchMembers(list);//批量添加 (执行sql语句批量增加)
            rsultMap.put("status", 1);
            rsultMap.put("data", "导入数据成功");
        } catch (Exception e) {
            rsultMap.put("status", -1);
            rsultMap.put("data", "导入数据异常");
        }

        return rsultMap;
    }

    private String getCellValue(Cell cell) {
        String value = "";
        if(cell != null) {
            //以下是判断数据的类型
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC://数字
                    value = cell.getNumericCellValue() + "";
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if(date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        }else {
                            value = "";
                        }
                    }else {
                        value = new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: //字符串
                    value = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: //boolean
                    value = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: //公式
                    value = cell.getCellFormula() + "";
                    break;
                case HSSFCell.CELL_TYPE_BLANK: //空值
                    value = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR: //故障
                    value = "非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }
        }
        return value.trim();
    }
}
