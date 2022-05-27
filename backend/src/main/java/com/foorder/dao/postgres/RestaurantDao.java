package com.foorder.dao.postgres;

import com.foorder.common.object.restaurant.ImmutableRestaurant;
import java.util.List;

public interface RestaurantDao {
    ImmutableRestaurant getRestaurantById(String id);
    void insertRestaurant(ImmutableRestaurant restaurant);
    void deleteRestaurant(String id);
    List<ImmutableRestaurant> getAllRestaurantsByCity(String cityName);
    List<ImmutableRestaurant> getAllRestaurantsByStreet(String streetName, String cityName);
}
