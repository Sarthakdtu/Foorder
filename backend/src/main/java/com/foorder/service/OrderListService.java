package com.foorder.service;

import com.foorder.model.order.OrderList;

public interface OrderListService {
    public OrderList getOrderListById(String orderId);
    public void insertOrderList(OrderList orderList);
}
