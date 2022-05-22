package com.foorder.service.impl;

import com.foorder.dao.postgres.RestaurantDao;
import com.foorder.model.Restaurant;
import com.foorder.model.location.Street;
import com.foorder.model.menu.Menu;
import com.foorder.service.MenuService;
import com.foorder.service.RestaurantService;
import com.foorder.service.StreetService;
import com.foorder.utils.LoggerService;
import com.foorder.utils.RandomStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    MenuService menuService;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    StreetService streetService;

    @Override
    public Restaurant getRestaurantById(String id) {
        return restaurantDao.getRestaurantById(id);
    }

    @Override
    public void insertRestaurant(Restaurant restaurant) {
        streetService.insertStreet(new Street(restaurant.getStreetName(),
                                              restaurant.getCityName(),
                                              RandomStrings.generatePincode()));
        Menu menu = new Menu(restaurant.getId());
        LoggerService.info("Creating menu for restaurant ID: " + restaurant.getId());
        menuService.insertMenu(menu);
        LoggerService.info("Menu created");
        restaurantDao.insertRestaurant(restaurant);
    }

    @Override
    public void deleteRestaurant(String id) {
        restaurantDao.deleteRestaurant(id);
    }

    @Override
    public List<Restaurant> getAllRestaurantsByCity(String cityName) {
        return restaurantDao.getAllRestaurantsByCity(cityName);
    }

    @Override
    public List<Restaurant> getAllRestaurantsByStreet(String streetName, String cityName) {
        return restaurantDao.getAllRestaurantsByStreet(streetName, cityName);
    }
}
