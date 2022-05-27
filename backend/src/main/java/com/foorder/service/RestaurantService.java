package com.foorder.service;

import com.foorder.common.object.restaurant.ImmutableRestaurant;


import java.util.List;

public interface RestaurantService {
    ImmutableRestaurant getRestaurantById(String id);
    void insertRestaurant(ImmutableRestaurant restaurant);
    void deleteRestaurant(String id);
    List<ImmutableRestaurant> getAllRestaurantsByCity(String cityName);
    List<ImmutableRestaurant> getAllRestaurantsByStreet(String streetName, String cityName);
}
