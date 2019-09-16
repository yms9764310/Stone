package com.jc.utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * 年: 2019
 * 月: 09
 * 日: 11
 * 小时: 21
 * 分钟: 20
 *
 * @author 严脱兔
 */
public class ExportDataToExcle {
    /**
     * 传入泛型为你要导出数据的resultTpye类型List集合，最后输出一个excle表格
     *
     * @param list      需要导出的数据
     * @param path      数据导出为文件下载后的地址和文件名字，也可以前端页面让用户自行选择下载地址
     * @param classpath List泛型的包名加类名
     * @return ResponseEntity<byte[]> 下载文件的返回值类型
     * @throws Exception
     */
    public static <E> HSSFWorkbook export(List<E> list, String path, String classpath) throws Exception {
        File file = new File(path);
        // 判断文件是否存在，如果存在则为之后的文件名进行重命名，防止文件覆盖
        if (file.exists()) {
            int i = path.lastIndexOf(".");
            path = path.substring(0, i);
            path = path + "(" + UUID.randomUUID().toString().replace("-", "").substring(0, 5) + ")" + ".xls";
        }
        // 创建工作簿对象，此处导出后文件后缀为.xls ，如需要也可改为XSSFWorkbook();后缀为.xlsx
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        // 如果需要对sheet表格进行样式改变，可自行设置

        @SuppressWarnings("unchecked")
        Class<E> cls = (Class<E>) Class.forName(classpath);

        // 获取到泛型类的所有属性
        Field[] fields = cls.getDeclaredFields();
        Object result = null;
        // 循环遍历生成表格的行数
        for (int i = 0; i < list.size(); i++) {
            Row row = null;
            // 标题
            if (i < 1) {
                row = sheet.createRow(i);
                for (int j = 0; j < fields.length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(fields[j].getName());
                }
            }
            // 内容
            row = sheet.createRow(i + 1);

            // 每一行对应属性个数的单元格
            for (int j = 0; j < fields.length; j++) {
                Cell cell = row.createCell(j);

                fields[j].setAccessible(true);
                // 获取每一个属性对应的get方法名
                String methodName = "get" + fields[j].getName().substring(0, 1).toUpperCase()
                        + fields[j].getName().substring(1);

                Method method = cls.getMethod(methodName);
                result = method.invoke(list.get(i));
                cell.setCellValue(String.valueOf(result));
            }
        }

        FileOutputStream fileOut = new FileOutputStream(path);
        wb.write(fileOut);

        // 以下代码主要用来对excle进行下载
        // 组装响应头部，主要针对contenttype
        HttpHeaders heanders = new HttpHeaders();
        // 在响应头中设置返回数据的类型，告诉浏览器该用什么样的方式处理当前返回的数据
        heanders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 在响应头中设置当前附件的名称
        heanders.setContentDispositionFormData("attachment", URLEncoder.encode(file.getName(), "UTF-8"));
        // 组装ResponseEntity，泛型是byte[]，所以第一个参数，也就是文件，需要转成byte[]
        // IOUtils.toByteArray() 把文件流转成数组
        // FileUtils.readFileToByteArray() 把文件转成数组
        byte[] bytes = FileUtils.readFileToByteArray(file);
        ResponseEntity<byte[]> res = new ResponseEntity<byte[]>(bytes, heanders, HttpStatus.OK);

        FileUtils.deleteQuietly(file);

        wb.close();
        fileOut.close();
        return wb;
    }

}
