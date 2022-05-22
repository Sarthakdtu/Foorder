package com.foorder.mapper;

import com.foorder.model.location.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityMapper implements RowMapper {

    @Override
    public City mapRow(ResultSet resultSet, int i) throws SQLException {
        return new City(resultSet.getString("name"));
    }
}
