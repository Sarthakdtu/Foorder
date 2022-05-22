package com.foorder.dao.postgres.impl;

import com.foorder.constants.Table;
import com.foorder.dao.postgres.RestaurantDao;
import com.foorder.mapper.RestaurantMapper;
import com.foorder.model.Restaurant;
import com.foorder.utils.LoggerService;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    NamedParameterJdbcTemplate template;

    public RestaurantDaoImpl(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    @Override
    public Restaurant getRestaurantById(String id) {
        String query = "SELECT * FROM " + Table.RESTAURANT.name +" WHERE id='%s'";
        query = String.format(query, id);
        LoggerService.info(query);
        return (Restaurant) template.query(query, new RestaurantMapper()).get(0);
    }

    @Override
    public void insertRestaurant(Restaurant restaurant) {
        final String query = "INSERT INTO " + Table.RESTAURANT.name +" (id, name, streetName, cityName) " +
                "VALUES(:id, :name, :streetName, :cityName)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", restaurant.getId())
                .addValue("name", restaurant.getName())
                .addValue("streetName", restaurant.getStreetName())
                .addValue("cityName", restaurant.getCityName());
        LoggerService.debug(query);
        template.update(query, param, keyHolder);
    }

    @Override
    public void deleteRestaurant(String id) {

    }

    @Override
    public List<Restaurant> getAllRestaurantsByCity(String cityName) {
        String query = "SELECT * FROM " + Table.RESTAURANT.name +" WHERE cityName='%s'";
        query = String.format(query, cityName);
        LoggerService.info(query);
        return (List<Restaurant>) template.query(query, new RestaurantMapper());
    }

    @Override
    public List<Restaurant> getAllRestaurantsByStreet(String streetName, String cityName) {
        String query = "SELECT * FROM " + Table.RESTAURANT.name +" WHERE cityName='%s' AND streetName='%s'";
        query = String.format(query, cityName, streetName);
        LoggerService.info(query);
        return (List<Restaurant>) template.query(query, new RestaurantMapper());
    }
}
