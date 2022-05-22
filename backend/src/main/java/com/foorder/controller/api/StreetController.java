package com.foorder.controller.api;

import com.foorder.model.location.Street;
import com.foorder.service.StreetService;
import com.foorder.utils.LoggerService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/street")
public class StreetController {

    @Autowired
    StreetService streetService;

    @GetMapping("")
    public Object getHealth(){
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("alive", true);
        return result;
    }

    @GetMapping("/get")
    public Street getStreetByName(@RequestParam String name, @RequestParam String cityName) throws SQLException, JSONException {
        Street street = null;
        try{
            street = streetService.getStreetById(name, cityName);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return street;
    }

    @GetMapping("/get-by-pincode")
    public List<Street> getStreetsByPincode(@RequestParam Long pincode){
        List<Street> streets = null;
        try{
            streets = streetService.getStreetsByPincode(pincode);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return streets;
    }

    @GetMapping("/get-all")
    public List<Street> getAllStreetsByCity(@RequestParam String cityName){
        List<Street> streets = null;
        try{
            streets = streetService.getAllStreetsByCity(cityName);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return streets;
    }

    @PostMapping("/create")
    public boolean createStreet(@RequestBody HashMap<String, String> req){
        boolean insert = false;
        try{
            String name = req.get("name");
            String cityName = req.get("cityName");
            Long pincode = Long.parseLong(req.get("pincode"));
            Street street = new Street(name, cityName, pincode);
            streetService.insertStreet(street);
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
        String cityName = req.get("cityName");
        boolean delete = false;
        try{
            streetService.deleteStreet(name, cityName);
            delete = true;
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return delete;
    }
}
