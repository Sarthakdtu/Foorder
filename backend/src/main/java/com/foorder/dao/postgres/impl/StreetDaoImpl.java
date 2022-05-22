package com.foorder.dao.postgres.impl;

import com.foorder.dao.postgres.StreetDao;
import com.foorder.mapper.StreetMapper;
import com.foorder.model.location.Street;
import com.foorder.utils.LoggerService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StreetDaoImpl implements StreetDao {

    NamedParameterJdbcTemplate template;

    public StreetDaoImpl(NamedParameterJdbcTemplate template){
        this.template = template;
    }


    @Override
    public Street getStreetById(String name, String cityName) {
        String query = new StringBuilder("SELECT * FROM STREET WHERE name='%s' AND cityName='%s'").toString();
        query = String.format(query, name, cityName);
        LoggerService.debug("Running Query = " + query);
        return (Street) template.query(query, new StreetMapper()).get(0);
    }

    @Override
    public List<Street> getStreetsByPincode(Long pincode) {
        String query = new StringBuilder("SELECT * FROM STREET WHERE pincode='%s'").toString();
        query = String.format(query, pincode.toString());
        LoggerService.debug("Running Query = " + query);
        return template.query(query, new StreetMapper());
    }

    @Override
    public void insertStreet(Street street) {
        final String query = "INSERT INTO STREET(name, cityName, pincode) " +
                "VALUES(:name, :cityName, :pincode) ON CONFLICT DO NOTHING";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", street.getName())
                .addValue("pincode", street.getPincode())
                .addValue("cityName", street.getCityName());
        LoggerService.debug(query);
        template.update(query, param, keyHolder);
    }

    @Override
    public void deleteStreet(String name, String cityName) {
        final String query = "DELETE FROM STREET WHERE name=:name AND cityName=:cityName";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("cityName", cityName);
        template.execute(query, map, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                return preparedStatement.executeUpdate();
            }
        });
    }

    @Override
    public List<Street> getAllStreetsCity(String cityName) {
        String query = new StringBuilder("SELECT * FROM STREET WHERE cityName='%s'").toString();
        query = String.format(query, cityName);
        LoggerService.debug("Running Query = " + query);
        return template.query(query, new StreetMapper());
    }
}
