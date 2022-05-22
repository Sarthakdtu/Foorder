package com.foorder.service;

import com.foorder.common.object.location.ImmutableCity;
import com.foorder.exceptions.ObjectDoesNotExistException;


import java.util.List;

public interface CityService {

    public ImmutableCity getCityByName(String name) throws ObjectDoesNotExistException;
    public void insertCity(ImmutableCity city);
    public void deleteCity(String name);
    public List<ImmutableCity> getAllCities();
}
