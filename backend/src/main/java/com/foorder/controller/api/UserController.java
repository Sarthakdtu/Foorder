package com.foorder.controller.api;

import com.foorder.model.UserProfile;
import com.foorder.service.UserProfileService;
import com.foorder.utils.LoggerService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user-profile")
public class UserController {

    @Autowired
    UserProfileService userProfileService;

    @GetMapping("")
    public Object getHealth(){
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("alive", true);
        return result;
    }

    @GetMapping("/get")
    public UserProfile getUserProfileByName(@RequestParam String username) throws SQLException, JSONException {
        UserProfile userProfile = null;
        try{
            userProfile = userProfileService.getUserProfileById(username);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return userProfile;
    }

    @GetMapping("/get-all")
    public List<UserProfile> getAllUserProfiles() throws SQLException, JSONException {
        List<UserProfile> userProfiles = null;
        try{
            userProfiles = userProfileService.getAllUserProfiles();
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return userProfiles;
    }

    @GetMapping("/get-city")
    public List<UserProfile> getCityUserProfiles(@RequestParam String cityName) throws SQLException, JSONException {
        List<UserProfile> userProfiles = null;
        try{
            userProfiles = userProfileService.getUserProfilesByCity(cityName);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return userProfiles;
    }

    @GetMapping("/get-street")
    public List<UserProfile> getCityUserProfiles(@RequestParam String cityName,
                                                 @RequestParam String streetName) throws SQLException, JSONException {
        List<UserProfile> userProfiles = null;
        try{
            userProfiles = userProfileService.getUserProfilesByStreet(streetName, cityName);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return userProfiles;
    }

    @PostMapping("/create")
    public boolean createUserProfile(@RequestBody HashMap<String, String> req) throws SQLException {
        boolean insert = false;
        try{
            String username = req.get("username");
            String cityName = req.get("cityName");
            String streetName = req.get("streetName");
            String houseNumber = req.get("houseNumber");
            String mobileNumber = req.getOrDefault("mobileNumber", null);
            boolean isBot = Boolean.parseBoolean(req.getOrDefault("isBot", String.valueOf(false)));
            UserProfile userProfile = UserProfile.builder()
                    .username(username)
                    .cityName(cityName)
                    .streetName(streetName)
                    .houseNumber(houseNumber)
                    .mobileNumber(mobileNumber)
                    .isBot(isBot)
                    .build();
            userProfileService.insertUserProfile(userProfile);
            insert = true;
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
            e.printStackTrace();
        }
        return insert;
    }

    @PutMapping("/update")
    public boolean updateUserProfile(@RequestBody UserProfile userProfile){
        boolean update = false;
        try{
            userProfileService.updateUserProfile(userProfile);
            update = true;
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return update;
    }

    @DeleteMapping("/delete")
    public boolean deleteUserProfile(@RequestBody HashMap<String, String> req){
        String username = req.get("username");
        boolean delete = false;
        try{
            userProfileService.deleteUserProfile(username);
            delete = true;
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return delete;
    }
}
