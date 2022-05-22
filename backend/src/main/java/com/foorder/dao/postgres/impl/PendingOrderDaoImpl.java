package com.foorder.dao.postgres.impl;

import com.foorder.constants.Table;
import com.foorder.dao.postgres.PendingOrderDao;
import com.foorder.mapper.PendingOrderMapper;
import com.foorder.model.order.Order;
import com.foorder.model.order.PendingOrder;
import com.foorder.utils.LoggerService;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PendingOrderDaoImpl implements PendingOrderDao {

    NamedParameterJdbcTemplate template;

    public PendingOrderDaoImpl(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    @Override
    public Order getOrderById(String id) {
        String query = "SELECT * FROM " + Table.PENDING_ORDERS.name +" WHERE id='%s'";
        query = String.format(query, id);
        LoggerService.info(query);
        return (Order) template.query(query, new PendingOrderMapper()).get(0);
    }

    @Override
    public void insertPendingOrder(PendingOrder order) {
        final String query = "INSERT INTO " + Table.PENDING_ORDERS.name +" (id, username, restaurantId, orderTime, price, pickUpTime) " +
                "VALUES(:id, :username, :restaurantId, :orderTime, :price, :pickUpTime)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", order.getId())
                .addValue("username", order.getUsername())
                .addValue("restaurantId", order.getRestaurantId())
                .addValue("orderTime", order.getOrderTime())
                .addValue("price", order.getPrice())
                .addValue("pickUpTime", order.getPickUpTime());
        LoggerService.debug(query);
        template.update(query, param, keyHolder);
    }

    @Override
    public void deletePendingOrder(String orderId) {

    }

    @Override
    public void insertDeliveredOrder(Order order) {

    }

    @Override
    public void deleteDeliveredOrder(String orderId) {

    }

    @Override
    public List<Order> getAllPendingOrdersByRestaurantId(String restaurantId) {
        return null;
    }

    @Override
    public List<Order> getAllDeliveredOrdersByRestaurantId(String restaurantId) {
        return null;
    }

    @Override
    public List<Order> getOrderHistoryByUser(String username) {
        return null;
    }

    @Override
    public List<Order> getOrderHistoryByRestaurant(String restaurantId) {
        return null;
    }

    @Override
    public List<Order> getAllPendingOrderByRestaurantId(String restaurantId) {
        return null;
    }

    @Override
    public List<Order> getAllPendingOrderByUsername(String username) {
        return null;
    }
}
