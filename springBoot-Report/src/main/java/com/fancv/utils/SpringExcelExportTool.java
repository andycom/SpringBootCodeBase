package com.fancv.utils;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel导出，Response对象生成工具。 生成spring Response对象。 用于导出Excel
 * <p>
 * 看了一些别人的说法，说是带泛型参数的变量要么是成员变量，要么是方法参数才能获取泛型参数类型，因为java在类的方面只提供了一个getGenericSuperClass()的方法，只能获取到父类的泛型参数类型。
 * 在成员变量和方法上还有getGenericType()和getGenericParameterTypes()方法。这是泛型的类型擦除导致的，具体我还没有探究。
 * 但这不是纯粹的泛型，因为要明确给出类型才能获取。如定义方法getName(List<String> lists)，使用反射可以很容易得到String类型，但如果把方法改为getName(List <T> lists)，
 * 无论你使用什么方法都不可能得到运行时给出的T的类型，只能得到字符串“T”。有人说有个什么框架（忘了）采用了这样一个方法：定义一个内部类，把T传给该类的成员变量，
 * 然后使用匿名内部类得到该成员变量，我试了，绕了一大圈，最后得到的还是“T”，要想得到运行时传递的泛型参数类型，就必须在方法中明确声明泛型类型。
 * 那么如果真的想使用纯粹的泛型参数（确实有这个需求，比如设计一些工具方法时）怎么办呢，其实有一种方法，不需要反射（如果用反射，代码还要多几行），
 * 就是加一个Class的参数，如getName(Class clazz, List<T> lists)，这样调用方法时给出clazz即可，而clazz就是T的Class。如果你想说这代码不够优雅，那我也没办法了
 *
 * @author
 */
@Slf4j
public class SpringExcelExportTool {


    public static <T> ResponseEntity<byte[]> genResponse(String fileName, String title, String sheetName, Class clazz, List exportList) {

        if (CollectionUtils.isEmpty(exportList)) {
            log.error("null Exception");
        }

        try (Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName),
                clazz, exportList)) {
            // StreamingOutput outputStreamImpl = new
            // ExcelStreamingOutputImpl(workbook);


            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(createWorkbook(workbook), headers, statusCode);
            return entity;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param fileName
     * @param exportList
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<byte[]> genCookieResponse(String fileName, String title, String sheetName, Class clazz, List<T> exportList) {

        if (CollectionUtils.isEmpty(exportList)) {
            log.error("");
        }

        try (Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName),
                clazz, exportList)) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.flush();
            workbook.write(output);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
            headers.add("Set-Cookie", "fileDownload=true;path=/");
            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(output.toByteArray(), headers, statusCode);
            return entity;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static class ExcelStreamingOutputImpl extends OutputStream {
        private Workbook workbook;

        public ExcelStreamingOutputImpl(Workbook workbook) {
            this.workbook = workbook;
        }

        public void write(OutputStream output) throws IOException {
            workbook.write(output);
        }

        @Override
        public void write(int b) throws IOException {

        }
    }

    /**
     * 创建xls文本
     * @return
     */
    public static byte[] createWorkbook(Workbook workbook){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes = bos.toByteArray();
        return bytes;
    }

}
