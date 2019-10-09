package com.jc.utils;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.HashMap;
import java.util.Map;

/**
 * 年: 2019
 * 月: 09
 * 日: 24
 * 小时: 09
 * 分钟: 38
 *
 * @author 严脱兔
 */
public class ExportUtil {

        private XSSFWorkbook wb = null;

        private XSSFSheet sheet = null;

        /**
         * @param wb
         * @param sheet
         */
        public ExportUtil(XSSFWorkbook wb, XSSFSheet sheet) {
            this.wb = wb;
            this.sheet = sheet;
        }

        /**
         * 合并单元格后给合并后的单元格加边框
         *
         * @param region
         * @param cs
         */
        public void setRegionStyle(CellRangeAddress region, XSSFCellStyle cs) {

            int toprowNum = region.getFirstRow();
            for (int i = toprowNum; i <= region.getLastRow(); i++) {
                XSSFRow row = sheet.getRow(i);
                for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                    XSSFCell cell = row.getCell(j);// XSSFCellUtil.getCell(row,
                    // (short) j);
                    cell.setCellStyle(cs);
                }
            }
        }

        /**
         * 设置表头的单元格样式
         *
         * @return
         */
        public XSSFCellStyle getHeadStyle() {
            // 创建单元格样式
            XSSFCellStyle cellStyle = wb.createCellStyle();
            // 设置单元格的背景颜色为淡蓝色
            cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
            cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            // 设置单元格居中对齐
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
            // 创建单元格内容显示不下时自动换行
            cellStyle.setWrapText(true);
            // 设置单元格字体样式
            XSSFFont font = wb.createFont();
            // 设置字体加粗
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            font.setFontName("宋体");
            font.setFontHeight((short) 200);
            cellStyle.setFont(font);
            // 设置单元格边框为细线条
            cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            return cellStyle;
        }

        /**
         * 设置表体的单元格样式
         *
         * @return
         */
        public XSSFCellStyle getBodyStyle() {
            // 创建单元格样式
            XSSFCellStyle cellStyle = wb.createCellStyle();
            // 设置单元格居中对齐
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            // 设置单元格垂直居中对齐
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
            // 创建单元格内容显示不下时自动换行
            cellStyle.setWrapText(true);
            // 设置单元格字体样式
            XSSFFont font = wb.createFont();
            // 设置字体加粗
//        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

            font.setFontName("宋体");
            font.setFontHeight((short) 200);
            cellStyle.setFont(font);
            // 设置单元格边框为细线条
            cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            return cellStyle;
        }

        /**
         * @param sheet
         * @param colIndex       合并的第几列  （下标从0开始）
         * @param startRowIndex  从第几行开始合并（算上标题，从0开始算）
         * @param endRowIndex    从第几行结束合并
         */
        public Map<String,String> mergerData(XSSFSheet sheet,int colIndex, int startRowIndex , int endRowIndex) {
            Map<String,String> map = new HashMap<>();

            breakFor:
            for (int i = startRowIndex; i <= endRowIndex; i++) {
                Cell cell = sheet.getRow(i).getCell(colIndex);

                for (int j = i + 1; j <= endRowIndex; j++) {
                    Cell celltemp = sheet.getRow(j).getCell(colIndex);

                    // 如果下一行与被比较行相等，则继续该循环，直到不等才跳出
                    if (!celltemp.getStringCellValue().equals(cell.getStringCellValue())) {
                        int temp = j-1;
                        if (temp > i) {
                            // 合并单元格
                            map.put(cell.getStringCellValue(), i+1+","+j);
                            sheet.addMergedRegion(new CellRangeAddress(i, temp, colIndex, colIndex));

                        }
                        i = temp;
                        break;
                    }
                    if (j == endRowIndex) {
                        map.put(cell.getStringCellValue(), i+1+","+j);
                        sheet.addMergedRegion(new CellRangeAddress(i, endRowIndex, colIndex, colIndex));
                        break breakFor;
                    }
                }
            }
            return map ;
        }
    }

