package com.foorder.dao.postgres.impl;

import com.foorder.common.object.restaurant.ImmutableRestaurant;
import com.foorder.dao.postgres.RestaurantDao;
import com.foorder.dao.postgres.Table;
import com.foorder.mapper.RestaurantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantDaoImpl.class);
    @Autowired
    private final JdbcTemplate template;

    public RestaurantDaoImpl(JdbcTemplate template){
        this.template = template;
    }

    @Override
    public ImmutableRestaurant getRestaurantById(String id) {
        String query = "SELECT * FROM " + Table.RESTAURANT.name +" WHERE id='%s'";
        query = String.format(query, id);
        logger.info(query);
        return (ImmutableRestaurant) template.query(query, new RestaurantMapper()).get(0);
    }

    @Override
    public void insertRestaurant(ImmutableRestaurant restaurant) {
        final String query = "INSERT INTO " + Table.RESTAURANT.name +" (id, name, street, city) " +
                "VALUES(:id, :name, :street, :city)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", restaurant.id())
                .addValue("name", restaurant.name())
                .addValue("street", restaurant.address().street().name())
                .addValue("city", restaurant.address().city().name());
        logger.debug(query);
        template.update(query, param, keyHolder);
    }

    @Override
    public void deleteRestaurant(String id) {

    }

    @Override
    public List<ImmutableRestaurant> getAllRestaurantsByCity(String city) {
        String query = "SELECT * FROM " + Table.RESTAURANT.name +" WHERE city='%s'";
        query = String.format(query, city);
        logger.info(query);
        return (List<ImmutableRestaurant>) template.query(query, new RestaurantMapper());
    }

    @Override
    public List<ImmutableRestaurant> getAllRestaurantsByStreet(String street, String city) {
        String query = "SELECT * FROM " + Table.RESTAURANT.name +" WHERE city='%s' AND street='%s'";
        query = String.format(query, city, street);
        logger.info(query);
        return (List<ImmutableRestaurant>) template.query(query, new RestaurantMapper());
    }
}
