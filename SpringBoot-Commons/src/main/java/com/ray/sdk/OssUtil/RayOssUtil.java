package com.ray.sdk.OssUtil;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.ray.sdk.config.ossConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Component
public class RayOssUtil {

    @Autowired
    OSS ossClient;

    @Autowired
    ossConfig ossConfig;

    /**
     * 上传文件
     *
     * @param name
     * @param
     * @return
     * @throws FileNotFoundException
     */
    public String putObject(String orgId, String name, InputStream inputStreame) throws IOException {
        String key = new StringBuilder(orgId).append(GetTime.getToday()).append(name).toString();
        PutObjectResult putObjectResult=ossClient.putObject(ossConfig.getBucketName(), key, inputStreame, getMetadata(inputStreame.available(), name));
        return key;
    }

    /**
     * 下载文件
     *
     * @param name
     * @return
     */
    public OSSObject getObject(String name) {
        return ossClient.getObject(ossConfig.getBucketName(), name);
    }

    /***
     * 删除文件
     */
    public Boolean deleteObject(String key) throws IOException {
        try {
            ossClient.deleteObject(ossConfig.getBucketName(), key);
        } catch (Exception e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


    /**
     * 要上传的文件类型
     * ---->需要判断文件后缀名
     */
    public ObjectMetadata getMetadata(Integer length, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        //这里需要判断文件后缀名
        metadata.setContentType(getContentType(fileName));
        metadata.setContentLength(length.longValue());
        metadata.setCacheControl("no-cache");
        metadata.setHeader("Pragma", "no-cache");
        metadata.setContentDisposition("inline;filename=" + fileName);
        return metadata;
    }

    /**
     * 判断要上传文件的后缀名
     */
    public static String getContentType(String fileName) {
        String filenameExtension = fileName.substring(fileName.lastIndexOf("."));
        if (filenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase(".jpeg") ||
                filenameExtension.equalsIgnoreCase(".jpg") ||
                filenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase(".pptx") ||
                filenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase(".docx") ||
                filenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        return "image/jpeg";
    }

}
