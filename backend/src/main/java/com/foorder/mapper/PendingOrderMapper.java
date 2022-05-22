package com.foorder.mapper;

import com.foorder.model.order.PendingOrder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PendingOrderMapper implements RowMapper {

    @Override
    public PendingOrder mapRow(ResultSet resultSet, int i) throws SQLException {
        String id = resultSet.getString("id");
        String username = resultSet.getString("username");
        String restaurantId = resultSet.getString("restaurantId");
        Double price = resultSet.getDouble("price");
        Timestamp orderTimestamp = (Timestamp) resultSet.getObject("orderTime");
        LocalDateTime orderTime = orderTimestamp.toLocalDateTime();
        Timestamp pickUpTimestamp = (Timestamp) resultSet.getObject("pickUpTime");
        LocalDateTime pickUpTime = pickUpTimestamp.toLocalDateTime();

        return new PendingOrder(id, username, restaurantId, orderTime, price, pickUpTime);
    }
}
