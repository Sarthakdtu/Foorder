package com.foorder.service;

import com.foorder.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    public Restaurant getRestaurantById(String id);
    public void insertRestaurant(Restaurant restaurant);
    public void deleteRestaurant(String id);
    public List<Restaurant> getAllRestaurantsByCity(String cityName);
    public List<Restaurant> getAllRestaurantsByStreet(String streetName, String cityName);
}
