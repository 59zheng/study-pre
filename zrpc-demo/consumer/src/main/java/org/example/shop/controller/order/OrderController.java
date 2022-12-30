package org.example.shop.controller.order;

import org.example.rpc.annotation.ZrpcRemote;
import org.example.shop.order.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @author: ts
 * @create:2021-05-10 11:30
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @ZrpcRemote
    private OrderService orderService;

    /**
     * 获取订单信息
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @GetMapping("/getOrder")
    public String getOrder(String userId, String orderNo) {
        System.out.println("1111 ");
        return orderService.getOrder(userId, orderNo);
    }

}
