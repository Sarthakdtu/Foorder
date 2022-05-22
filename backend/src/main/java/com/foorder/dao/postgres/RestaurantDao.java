package com.foorder.dao.postgres;

import com.foorder.model.Restaurant;

import java.util.List;

public interface RestaurantDao {
    public Restaurant getRestaurantById(String id);
    public void insertRestaurant(Restaurant restaurant);
    public void deleteRestaurant(String id);
    public List<Restaurant> getAllRestaurantsByCity(String cityName);
    public List<Restaurant> getAllRestaurantsByStreet(String streetName, String cityName);
}
