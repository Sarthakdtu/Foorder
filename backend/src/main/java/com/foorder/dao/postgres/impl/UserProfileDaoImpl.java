package com.foorder.dao.postgres.impl;

import com.foorder.common.object.user.ImmutableUserProfile;
import com.foorder.dao.postgres.UserProfileDao;
import com.foorder.mapper.UserProfileMapper;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserProfileDaoImpl implements UserProfileDao {

    NamedParameterJdbcTemplate template;

    public UserProfileDaoImpl(NamedParameterJdbcTemplate template){
        this.template = template;
    }

    @Override
    public List<ImmutableUserProfile> getAllUserProfiles() {
        String query = "SELECT * FROM " + Table.USER_PROFILE.name;
        return (List<ImmutableUserProfile>) template.query(query, new UserProfileMapper());
    }

    @Override
    public List<ImmutableUserProfile> getAllBots() {
        String query = "SELECT * FROM " + Table.USER_PROFILE.name + " WHERE isBot=True";
        return (List<ImmutableUserProfile>) template.query(query, new UserProfileMapper());
    }

    @Override
    public ImmutableUserProfile getUserProfileById(String username) {
        String query = "SELECT * FROM " + Table.USER_PROFILE.name +" WHERE username='%s'";
        query = String.format(query, username);
        logger.info(query);
        return (UserProfile) template.query(query, new UserProfileMapper()).get(0);
    }

    @Override
    public List<ImmutableUserProfile> getUserProfilesByCity(String cityName) {
        String query = "SELECT * FROM " + Table.USER_PROFILE.name +" WHERE cityName='%s'";
        query = String.format(query, cityName);
        logger.info(query);
        return (List<ImmutableUserProfile>) template.query(query, new UserProfileMapper());
    }

    @Override
    public List<ImmutableUserProfile> getUserProfilesByStreet(String streetName, String cityName) {
        String query = "SELECT * FROM " + Table.USER_PROFILE.name +" WHERE cityName='%s' AND streetName='%s'";
        query = String.format(query, cityName, streetName);
        logger.info(query);
        return (List<ImmutableUserProfile>) template.query(query, new UserProfileMapper());
    }

    @Override
    public void insertUserProfile(ImmutableUserProfile userProfile) {
        final String query = "INSERT INTO " + Table.USER_PROFILE.name +" (username, houseNumber, cityName, streetName, mobileNumber) " +
                "VALUES(:username, :houseNumber, :cityName, :streetName, :mobileNumber)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", userProfile.getUsername())
                .addValue("houseNumber", userProfile.getHouseNumber())
                .addValue("streetName", userProfile.getStreetName())
                .addValue("cityName", userProfile.getCityName())
                .addValue("mobileNumber", userProfile.getMobileNumber());
        logger.debug(query);
        template.update(query, param, keyHolder);
    }

    @Override
    public void updateUserProfile(ImmutableUserProfile userProfile) {
        final String query = "UPDATE " + Table.USER_PROFILE.name + " SET username= :username, houseNumber= :houseNumber, cityName= :cityName, " +
                "streetName= :streetName, mobileNumber= :mobileNumber"
                + " WHERE username= :username";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", userProfile.getUsername())
                .addValue("houseNumber", userProfile.getHouseNumber())
                .addValue("streetName", userProfile.getStreetName())
                .addValue("cityName", userProfile.getCityName())
                .addValue("mobileNumber", userProfile.getMobileNumber());
        logger.debug(query);
        template.update(query, param, keyHolder);
    }

    @Override
    public void deleteUserProfile(String username) {
        final String query = "DELETE FROM " + Table.USER_PROFILE.name + " WHERE username=:username";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        template.execute(query, map, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                return preparedStatement.executeUpdate();
            }
        });
    }


}
