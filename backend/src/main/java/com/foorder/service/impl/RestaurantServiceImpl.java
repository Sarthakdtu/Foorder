package com.foorder.service.impl;

import com.foorder.common.object.location.ImmutableStreet;
import com.foorder.common.object.menu.ImmutableMenu;
import com.foorder.common.object.restaurant.ImmutableRestaurant;
import com.foorder.dao.postgres.RestaurantDao;
import com.foorder.service.MenuService;
import com.foorder.service.RestaurantService;
import com.foorder.service.StreetService;

import com.foorder.utils.RandomStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    @Autowired
    MenuService menuService;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    StreetService streetService;

    @Override
    public ImmutableRestaurant getRestaurantById(String id) {
        return restaurantDao.getRestaurantById(id);
    }

    @Override
    public void insertRestaurant(ImmutableRestaurant restaurant) {
        ImmutableMenu menu = ImmutableMenu.builder().restaurantId(restaurant.id()).build();
        logger.info("Creating menu for restaurant ID: {}", restaurant.id());
        menuService.insertMenu(menu);
        restaurantDao.insertRestaurant(restaurant);
    }

    @Override
    public void deleteRestaurant(String id) {
        restaurantDao.deleteRestaurant(id);
    }

    @Override
    public List<ImmutableRestaurant> getAllRestaurantsByCity(String cityName) {
        return restaurantDao.getAllRestaurantsByCity(cityName);
    }

    @Override
    public List<ImmutableRestaurant> getAllRestaurantsByStreet(String streetName, String cityName) {
        return restaurantDao.getAllRestaurantsByStreet(streetName, cityName);
    }
}
