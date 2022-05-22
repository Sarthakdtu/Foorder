package com.foorder.mapper;

import com.foorder.model.location.Street;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StreetMapper implements RowMapper {

    @Override
    public Street mapRow(ResultSet resultSet, int i) throws SQLException {
        String name = resultSet.getString("name");
        Long pincode = resultSet.getLong("pincode");
        String cityName = resultSet.getString("cityName");
        return new Street(name, cityName, pincode);
    }
}
