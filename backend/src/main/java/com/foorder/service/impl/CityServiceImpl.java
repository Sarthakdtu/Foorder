package com.foorder.service.impl;

import com.foorder.common.object.location.ImmutableCity;
import com.foorder.dao.postgres.CityDao;
import com.foorder.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityDao cityDao;

    @Override
    public ImmutableCity getCityByName(String name) {
        return cityDao.getCityByName(name);
    }

    @Override
    public void insertCity(ImmutableCity city) {
        cityDao.insertCity(city);
    }

    @Override
    public void deleteCity(String name) {
        cityDao.deleteCity(name);
    }

    @Override
    public List<ImmutableCity> getAllCities() {
        return cityDao.getAllCities();
    }
}
