package com.example.springbootmall.controller;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.model.OmsOrder;
import com.example.springbootmall.service.OmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Api(tags = "OmsOrderController")
@Tag(name = "OmsOrderController", description = "订单管理")
@RequestMapping("/order")
public class OmsOrderController {
    @Autowired
    private OmsOrderService omsOrderService;

    @ApiOperation(("创建订单"))
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public CommonResult<OmsOrder> addOrder() {
        OmsOrder order = new OmsOrder();
        order.setUserId(1L);
        order.setStatus(0);
        order.setCreateTime(new Date());
        int count = omsOrderService.addOrder(order);
        if (count > 0) {
            return CommonResult.success(order);
        }
        else {
            return CommonResult.failed("failed");
        }
    }

}
