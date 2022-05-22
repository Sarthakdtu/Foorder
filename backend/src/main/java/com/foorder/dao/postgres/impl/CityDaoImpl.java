package com.foorder.dao.postgres.impl;

import com.foorder.common.object.location.ImmutableCity;
import com.foorder.dao.postgres.CityDao;
import com.foorder.mapper.CityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CityDaoImpl implements CityDao {

    private static final Logger logger = LoggerFactory.getLogger(CityDaoImpl.class);

    @Override
    public ImmutableCity getCityByName(String name) {
        return null;
    }

    @Override
    public void insertCity(ImmutableCity city) {

    }

    @Override
    public void deleteCity(String name) {

    }

    @Override
    public List<ImmutableCity> getAllCities() {
        return null;
    }

//    NamedParameterJdbcTemplate template;
//
//    public CityDaoImpl(NamedParameterJdbcTemplate template){
//        this.template = template;
//    }
//
//    @Override
//    public ImmutableCity getCityByName(String name) {
//        String query = new StringBuilder("SELECT * FROM CITY WHERE name='%s'").toString();
//        query = String.format(query, name);
//        return (ImmutableCity) template.query(query, new CityMapper()).get(0);
//    }
//
//    @Override
//    public void insertCity(ImmutableCity city) {
//        final String query = "INSERT INTO CITY(name) " +
//                "VALUES(:name)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        SqlParameterSource param = new MapSqlParameterSource()
//                .addValue("name", city.getName());
//        logger.debug(query);
//        template.update(query, param, keyHolder);
//    }
//
//    @Override
//    public void deleteCity(String name) {
//        final String query = "DELETE FROM CITY WHERE name=:name";
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("name", name);
//        template.execute(query, map, new PreparedStatementCallback<Object>() {
//            @Override
//            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
//                return preparedStatement.executeUpdate();
//            }
//        });
//    }
//
//    @Override
//    public List<ImmutableCity> getAllCities() {
//        return template.query("SELECT * FROM CITY ", new CityMapper());
//    }
}
