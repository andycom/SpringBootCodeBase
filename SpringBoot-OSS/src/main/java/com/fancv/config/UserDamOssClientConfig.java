package com.fancv.config;

import com.raycloud.qnee.commons.api.top.oss.OSSCommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class UserDamOssClientConfig {

    @Value("${dam.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${dam.oss.accessKeySecret}")
    private String appKey;
    @Value("${dam.oss.bucketName}")
    private String bucket;
    @Value("${dam.oss.ossEndPoint}")
    private String endPoint;
    @Value("${dam.oss.ossEndPointInner}")
    private String endPointInner;

    public UserDamOssClientConfig() {
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Bean(name = "damOpenOssCommonUtils")
    @Scope("prototype")
    public OSSCommonUtils ossPublicCommonUtils() {
        OSSCommonUtils ossCommonUtils= new OSSCommonUtils(this.accessKeyId, this.appKey, this.endPoint, this.endPointInner, this.bucket);
        return ossCommonUtils;
    }
}
