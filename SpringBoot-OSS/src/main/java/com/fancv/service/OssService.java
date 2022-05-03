package com.fancv.service;

import com.ray.sdk.OssUtil.RayOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class OssService {

    @Autowired
    RayOssUtil rayOssUtil;


    public String putObject(String name, InputStream inputStream) throws IOException {

        return rayOssUtil.putObject("exampledir/", "/" + name, inputStream);
    }

    public Boolean deleteObject(String name) throws IOException {

        return rayOssUtil.deleteObject(name);
    }
}
