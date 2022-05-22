package com.foorder.service;

import com.foorder.model.order.DetailedOrder;
import com.foorder.model.order.Order;
import com.foorder.model.order.OrderItem;
import com.foorder.model.order.PendingOrder;

import java.util.HashMap;
import java.util.List;

public interface OrderService {

    Order getOrderById(String id);
    DetailedOrder getOrderDetailsById(String id);
    void insertPendingOrder(PendingOrder order, List<OrderItem> items) throws Exception;
    void deletePendingOrder(String orderId);
    void insertDeliveredOrder(Order order);
    void deleteDeliveredOrder(String orderId);
    List<Order> getAllPendingOrdersByRestaurantId(String restaurantId);
    List<Order> getAllDeliveredOrdersByRestaurantId(String restaurantId);
    List<Order> getOrderHistoryByUser(String username);
    List<Order> getOrderHistoryByRestaurant(String restaurantId);
    HashMap<String, Object> validateOrder(Order order, List<OrderItem> items, HashMap<String, Integer> idTtmMap) throws Exception;
    List<Order> getAllPendingOrderByRestaurantId(String restaurantId);
    List<Order> getAllPendingOrderByUsername(String username);
}
