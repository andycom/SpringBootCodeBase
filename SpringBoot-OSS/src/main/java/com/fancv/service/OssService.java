package com.fancv.service;

import com.ray.sdk.OssUtil.RayOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
public class OssService {

    @Autowired
    RayOssUtil rayOssUtil;


    public String putObject(InputStream inputStream) throws FileNotFoundException {

         return  rayOssUtil.putObject("0001","test",inputStream);
    }
}
