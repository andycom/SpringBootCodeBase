package com.fancv.controller;

import com.fancv.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("oss")
@Api(tags = "1")
public class ossController {


    @Autowired
    OssService ossService;

    @PostMapping("/file")
    public String put(@RequestParam("file") MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();
        String name = file.getOriginalFilename();
        return ossService.putObject(name, inputStream);
    }

    @PostMapping("/delete")
    public Boolean put(@RequestParam("key") String key) throws IOException {

        return ossService.deleteObject(key);
    }
}
