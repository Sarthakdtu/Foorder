package com.foorder.service.impl;

import com.foorder.dao.postgres.StreetDao;
import com.foorder.model.location.Street;
import com.foorder.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreetServiceImpl implements StreetService {

    @Autowired
    StreetDao streetDao;

    @Override
    public Street getStreetById(String name, String cityName) {
        return streetDao.getStreetById(name, cityName);
    }

    @Override
    public List<Street> getStreetsByPincode(Long pincode) {
        return streetDao.getStreetsByPincode(pincode);
    }

    @Override
    public void insertStreet(Street street) {
        streetDao.insertStreet(street);
    }

    @Override
    public void deleteStreet(String name, String cityName) {
        streetDao.deleteStreet(name, cityName);
    }

    @Override
    public List<Street> getAllStreetsByCity(String cityName) {
        return streetDao.getAllStreetsCity(cityName);
    }
}
