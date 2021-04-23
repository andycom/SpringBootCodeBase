package com.fancv.report.controller;

import com.fancv.report.VO.User;
import com.fancv.utils.SpringExcelExportTool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("report")
public class ReportController {


    @GetMapping("excel")
    public ResponseEntity<byte[]> reportExcel() {
        User u = new User();
        u.setAge(12);
        u.setName("陈依依");
        u.setReamrk("我的备注");
        List<User> ulist = new ArrayList<>(8);

        User u1 = new User();
        u1.setAge(12);
        u1.setName("陈依依");
        u1.setReamrk("我的备注");
        ulist.add(u1);
        ulist.add(u);

        return SpringExcelExportTool.genResponse("测试", "bn", "A", u.getClass(), ulist);
    }
}
