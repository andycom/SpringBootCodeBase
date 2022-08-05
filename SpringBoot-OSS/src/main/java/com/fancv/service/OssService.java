package com.fancv.service;

import com.raycloud.sheji.dto.DamFileUpdateDto;
import com.raycloud.sheji.util.DamOpenOssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class OssService {

    @Autowired
    DamOpenOssUtils damOpenOssUtils;

    public OssService() {
        super();
    }

    public DamFileUpdateDto putObject(String name, InputStream inputStream) throws Exception {

       return damOpenOssUtils.uploadFileAndThumbnailToOss("exampledir",name,inputStream,false);


    }

    public Boolean fileExist(String name) throws IOException {

        return damOpenOssUtils.fileExist(name);
    }
}
