package com.foorder.dao.postgres;

import com.foorder.common.object.location.ImmutableCity;

import java.util.List;

public interface CityDao {
    ImmutableCity getCityByName(String name);
    void insertCity(ImmutableCity city);

    void deleteCity(String name);
    List<ImmutableCity> getAllCities();
}
