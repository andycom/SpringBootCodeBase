package com.fancv.controller;

import com.fancv.service.LTSService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@Api(tags = "1")
public class LTSController {

    @Autowired
    LTSService ltsService;

    @PostMapping("timer")
    public void  ltsTask(@RequestParam String time) throws ParseException {
        ltsService.submitWithTrigger(time);
    }
}
