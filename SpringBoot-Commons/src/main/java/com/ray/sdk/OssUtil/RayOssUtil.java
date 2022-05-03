package com.ray.sdk.OssUtil;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.ray.sdk.config.ossConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public String putObject(String orgId, String name, InputStream inputStreame) throws FileNotFoundException {
        String key = new StringBuilder(orgId).append(GetTime.getToday()).append(name).toString();
        ossClient.putObject(ossConfig.getBucketName(), key, inputStreame);
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


}
