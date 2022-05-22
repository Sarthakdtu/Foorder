package com.foorder.controller.api;


import com.foorder.common.object.location.ImmutableCity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/city")
public class CityController {

    private static Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    CityService cityService;

    @GetMapping("")
    public Object getHealth(){
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("alive", true);
        return result;
    }

    @GetMapping("/get")
    public ImmutableCity getCityByName(@RequestParam String name) throws SQLException, JSONException {
        City city = null;
        try{
            city = cityService.getCityByName(name);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return city;
    }

    @GetMapping("/get-all")
    public List<City> getAllCities() throws SQLException, JSONException {
        List<City> cities = null;
        try{
            cities = cityService.getAllCities();
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return cities;
    }

    @PostMapping("/create")
    public boolean createCity(@RequestBody HashMap<String, String> req) throws SQLException {
        boolean insert = false;
        try{
            String name = req.get("name");
            City city = new City(name);
            cityService.insertCity(city);
            insert = true;
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
            e.printStackTrace();
        }
        return insert;
    }

    @DeleteMapping("/delete")
    public boolean deleteCity(@RequestBody HashMap<String, String> req){
        String name = req.get("name");
        boolean delete = false;
        try{
            cityService.deleteCity(name);
            delete = true;
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return delete;
    }
}
