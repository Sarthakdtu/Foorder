package com.foorder.mapper;

import com.foorder.common.object.location.ImmutableAddress;
import com.foorder.common.object.location.ImmutableBlock;
import com.foorder.common.object.location.ImmutableCity;
import com.foorder.common.object.location.ImmutableStreet;
import com.foorder.common.object.restaurant.ImmutableRestaurant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantMapper implements RowMapper {
    @Override
    public ImmutableRestaurant mapRow(ResultSet resultSet, int i) throws SQLException {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        String cityName = resultSet.getString("city");
        String streetName = resultSet.getString("street");
        String blockName = resultSet.getString("block");
        String houseNumber = resultSet.getString("houseNumber");

        ImmutableCity city = ImmutableCity.builder().name(cityName).build();
        ImmutableStreet street = ImmutableStreet.builder().name(streetName).build();
        ImmutableBlock block = ImmutableBlock.builder().name(blockName).build();
        ImmutableAddress address = ImmutableAddress.builder().city(city).street(street).block(block).houseNumber(houseNumber).build();
        return ImmutableRestaurant.builder().id(id).name(name).address(address).build();
    }
}
