package com.fancv.controller;

import com.fancv.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("test")
@Api(tags = "1")
public class ossController {


    @Autowired
    OssService ossService;

    @PostMapping("/file")
    public String put(String mess, MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();
        return ossService.putObject(inputStream);
    }
}
