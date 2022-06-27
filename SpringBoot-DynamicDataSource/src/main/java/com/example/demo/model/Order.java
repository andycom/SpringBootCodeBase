package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class Order implements Serializable {


    private static final long serialVersionUID = 9009670891546120887L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    private Long orderId;

    private String remark;

    private Date createTime;

}
