package com.fancv.utils;


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
import java.util.Date;
import java.util.List;

/**
 * Excel导出，Response对象生成工具。 生成spring Response对象。 用于导出Excel
 * TODO
 * 改写成eazy POI 版本
 * @author
 */
@Slf4j
public class SpringExcelExportTool {


    public static <T> ResponseEntity<byte[]> genResponse(String fileName, List<T> exportList) {

        if (CollectionUtils.isEmpty(exportList)) {
            log.error("null Exception");
        }

        try (Workbook workbook = ExcelUtils.generate(exportList, true)) {
            fileName = fileName + DateUtil.formatDate(new Date(), DateUtil.TIME_PATTON_YYYYMMDDHHMMSS);
            // StreamingOutput outputStreamImpl = new
            // ExcelStreamingOutputImpl(workbook);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.flush();
            workbook.write(output);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(output.toByteArray(), headers, statusCode);
            return entity;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     *
     * @param fileName
     * @param exportList
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<byte[]> genCookieResponse(String fileName, List<T> exportList) {

        if (CollectionUtils.isEmpty(exportList)) {
            log.error("");
        }

        try (Workbook workbook = ExcelUtils.generate(exportList, true)) {
            fileName = fileName + DateUtil.formatDate(new Date(), DateUtil.TIME_PATTON_YYYYMMDDHHMMSS);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.flush();
            workbook.write(output);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            headers.add("Set-Cookie", "fileDownload=true;path=/");
            HttpStatus statusCode = HttpStatus.OK;
            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(output.toByteArray(), headers, statusCode);
            return entity;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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

}
