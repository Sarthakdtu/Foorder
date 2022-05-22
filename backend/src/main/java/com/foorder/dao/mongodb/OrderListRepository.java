package com.foorder.dao.mongodb;

import com.foorder.model.order.OrderList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface OrderListRepository extends MongoRepository<OrderList, String> {

    @Query("{_id:'?0'}")
    OrderList findOrderListByOrderID(String orderId);

    long count();
}
