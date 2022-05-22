package com.foorder.service.impl;

import com.foorder.dao.mongodb.OrderListRepository;
import com.foorder.model.order.OrderList;
import com.foorder.service.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderListServiceImpl implements OrderListService {

    @Autowired
    OrderListRepository orderListRepository;

    @Override
    public OrderList getOrderListById(String orderId) {
        return orderListRepository.findOrderListByOrderID(orderId);
    }

    @Override
    public void insertOrderList(OrderList orderList) {
        orderListRepository.save(orderList);
    }
}
