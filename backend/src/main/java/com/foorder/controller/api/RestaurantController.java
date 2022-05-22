package com.foorder.controller.api;

import com.foorder.model.Restaurant;
import com.foorder.service.RestaurantService;
import com.foorder.utils.LoggerService;
import com.foorder.utils.RandomStrings;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("")
    public Object getHealth(){
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("alive", true);
        return result;
    }

    @GetMapping("/get")
    public Restaurant getRestaurantById(@RequestParam String id) throws SQLException, JSONException {
        Restaurant restaurant = null;
        try{
            restaurant = restaurantService.getRestaurantById(id);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return restaurant;
    }

    @GetMapping("/get-all-city")
    public List<Restaurant> getAllRestaurantByCity(@RequestParam String cityName) throws SQLException, JSONException {
        List<Restaurant> restaurants = null;
        try{
            restaurants = restaurantService.getAllRestaurantsByCity(cityName);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return restaurants;
    }

    @GetMapping("/get-all-street")
    public List<Restaurant> getAllRestaurantByStreet(@RequestParam String streetName, @RequestParam String cityName) throws SQLException, JSONException {
        List<Restaurant> restaurants = null;
        try {
            restaurants = restaurantService.getAllRestaurantsByStreet(streetName, cityName);
        } catch (Exception e) {
            LoggerService.error(e.getMessage());
        }
        return restaurants;
    }

        @PostMapping("/create")
    public String createRestaurant(@RequestBody HashMap<String, String> req) throws SQLException {

        String id = RandomStrings.generateRestaurantId();
        try{
            String name = req.get("name");
            String streetName = req.get("streetName");
            String cityName = req.get("cityName");
            Restaurant restaurant = new Restaurant(id, name, streetName, cityName);
            restaurantService.insertRestaurant(restaurant);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    @DeleteMapping("/delete")
    public boolean deleteRestaurant(@RequestBody HashMap<String, String> req){
        String id = req.get("id");
        boolean delete = false;
        try{
            restaurantService.deleteRestaurant(id);
            delete = true;
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return delete;
    }
}
