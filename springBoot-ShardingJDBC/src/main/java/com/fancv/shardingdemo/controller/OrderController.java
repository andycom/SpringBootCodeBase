package com.fancv.shardingdemo.controller;

import com.fancv.shardingdemo.model.Order;
import com.fancv.shardingdemo.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/demo")
@RestController
@Api(value = "/user", description = "Operations about user")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/add")
    @ApiOperation("批量写入数据")
    public String addBatch(@RequestParam Long id) {

        for (int i = 10; i < 20; i++) {
            Order order = new Order();
            long userId = id + i;
            long orderId = id + i;
            order.setUserId(userId);
            order.setOrderId(orderId);
            order.setRemark("remark:" + i);
            orderService.addOrder(order);

        }

        return "success";
    }

    @PostMapping("/get")
    @ApiOperation("查询数据")
    public List<Order> getOrderInfo(@RequestParam Long id) {
        return orderService.getOrderByOrderId(id);
    }
}
