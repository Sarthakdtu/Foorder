package com.foorder.controller.api;


import com.foorder.common.object.location.ImmutableCity;
import com.foorder.exceptions.ObjectDoesNotExistException;
import com.foorder.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.foorder.utils.ResponseBuilder.*;

@RestController
@RequestMapping("api/v1/city")
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    CityService cityService;

    @GetMapping("")
    public ResponseEntity<?> getHealth(){
        return buildGetResponse("alive");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCityByName(@RequestParam String name) {
        try{
            ImmutableCity city = cityService.getCityByName(name);
            return buildGetResponse(city);
        }
        catch (ObjectDoesNotExistException e){
            logger.error("City {} does not exist", name);
            e.printStackTrace();
            return buildErrorResponse(String.join(" ", name, e.getMessage()), false);
        }
        catch (Exception e){
            logger.error("Internal error");
            e.printStackTrace();
            return buildErrorResponse(String.join(" ", name, e.getMessage()), true);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCities() throws Exception {
        try{
            List<ImmutableCity> cities = cityService.getAllCities();
            return buildGetResponse(cities);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return buildGetResponse(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCity(@RequestBody HashMap<String, String> req) throws Exception {
        try{
            String name = req.get("name");
            ImmutableCity city = ImmutableCity.builder().name(name).build();
            cityService.insertCity(city);
            return buildPostResponse(name);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return buildErrorResponse(e.getMessage(), true);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCity(@RequestBody HashMap<String, String> req){
        String name = req.get("name");
        try{
            cityService.deleteCity(name);
            return buildDeleteResponse(name);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return buildErrorResponse(e.getMessage(), true);
        }
    }
}
