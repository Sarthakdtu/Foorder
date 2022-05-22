package com.foorder.dao.postgres;

import com.foorder.model.order.Order;
import com.foorder.model.order.PendingOrder;

import java.util.List;

public interface PendingOrderDao {
    Order getOrderById(String id);
    void insertPendingOrder(PendingOrder order);
    void deletePendingOrder(String orderId);
    void insertDeliveredOrder(Order order);
    void deleteDeliveredOrder(String orderId);
    List<Order> getAllPendingOrdersByRestaurantId(String restaurantId);
    List<Order> getAllDeliveredOrdersByRestaurantId(String restaurantId);
    List<Order> getOrderHistoryByUser(String username);
    List<Order> getOrderHistoryByRestaurant(String restaurantId);
    List<Order> getAllPendingOrderByRestaurantId(String restaurantId);
    List<Order> getAllPendingOrderByUsername(String username);
}
