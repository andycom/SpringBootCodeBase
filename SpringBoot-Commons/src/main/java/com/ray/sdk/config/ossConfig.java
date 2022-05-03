package com.ray.sdk.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ossConfig {

    @Value("${rayOssKey}")
    private String rayOssKey;

    @Value("${rayOssSecret}")
    private String rayOssSecret;

    @Value("${rayOssEndpoint}")
    private String ossEndpoint;

    @Value("${rayBucketName}")
    private String bucketName;


    @Bean
    public OSS getOssObject(){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-zhangjiakou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tDT2HxdMRBVV8pE1MNv";
        String accessKeySecret = "1whvEgrT0t7jo2vJxi0QgmJ0aWnzyH";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "fancv-sys";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。 按照orgId 年月日存储文件
        String objectName = "exampledir3/1exampleobject.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(rayOssKey,rayOssSecret, ossEndpoint);

        return ossClient;

    }
}
