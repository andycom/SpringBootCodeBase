package com.fancv.report.VO;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class User {

    @Excel(name = "姓名")
    private String name;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "备注", width = 20.0d)
    private String reamrk;
}
