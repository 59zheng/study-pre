package org.example.shop.order;

public interface OrderService {


    /**
     * 获取订单描述信息
     * @param userId
     * @param orderNo
     * @return
     */
    String getOrder(String userId, String orderNo);
}
