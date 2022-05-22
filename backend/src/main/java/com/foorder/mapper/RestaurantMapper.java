package com.foorder.mapper;

import com.foorder.model.Restaurant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantMapper implements RowMapper {
    @Override
    public Restaurant mapRow(ResultSet resultSet, int i) throws SQLException {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        String cityName = resultSet.getString("cityName");
        String streetName = resultSet.getString("streetName");

        Restaurant restaurant = new Restaurant(id, name, streetName, cityName);
        return restaurant;
    }
}
