package com.foorder.service;

import com.foorder.model.location.Street;

import java.util.List;

public interface StreetService {
    public Street getStreetById(String name, String cityName);
    public List<Street> getStreetsByPincode(Long pincode);
    public void insertStreet(Street street);
    public void deleteStreet(String name, String cityName);
    public List<Street> getAllStreetsByCity(String cityName);
}
