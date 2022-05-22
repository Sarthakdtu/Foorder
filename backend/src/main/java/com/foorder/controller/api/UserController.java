package com.foorder.controller.api;

import com.foorder.common.object.location.ImmutableAddress;
import com.foorder.common.object.location.ImmutableBlock;
import com.foorder.common.object.location.ImmutableCity;
import com.foorder.common.object.location.ImmutableStreet;
import com.foorder.common.object.user.ImmutableMobileNumber;
import com.foorder.common.object.user.ImmutableUserProfile;
import com.foorder.common.object.user.UserProfile;
import com.foorder.service.UserProfileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static com.foorder.utils.ResponseBuilder.buildErrorResponse;
import static com.foorder.utils.ResponseBuilder.buildPostResponse;

@RestController
@RequestMapping("/api/v1/user-profile")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserProfileService userProfileService;

    @GetMapping("")
    public Object getHealth(){
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("alive", true);
        return result;
    }

    @GetMapping("/get")
    public ImmutableUserProfile getUserProfileByName(@RequestParam String username) throws SQLException, JSONException {
        ImmutableUserProfile userProfile = null;
        try{
            userProfile = userProfileService.getUserProfileById(username);
        }
        catch (Exception e){
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
        }
        return userProfiles;
    }

    private ImmutableUserProfile parseUserProfile(HashMap<String, String> req){
        String username = req.get("username");
        String cityName = req.get("cityName");
        String streetName = req.get("streetName");
        String blockName = req.get("block");
        String houseNumber = req.get("houseNumber");
        String mobileNumber = req.getOrDefault("mobileNumber", null);
        boolean isBot = Boolean.parseBoolean(req.getOrDefault("isBot", String.valueOf(false)));
        ImmutableCity city = ImmutableCity.builder().name(cityName).build();
        ImmutableStreet street = ImmutableStreet.builder().name(streetName).build();
        ImmutableBlock block = ImmutableBlock.builder().name(blockName).build();
        ImmutableAddress address = ImmutableAddress.builder()
                .city(city)
                .street(street)
                .block(block)
                .houseNumber(houseNumber)
                .build();
        ImmutableMobileNumber mobile = ImmutableMobileNumber.builder().number(mobileNumber).build();
        return ImmutableUserProfile.builder()
                .username(username)
                .address(address)
                .mobileNumber(mobile)
                .isBot(isBot)
                .build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUserProfile(@RequestBody HashMap<String, String> req) throws SQLException {
        boolean insert = false;
        try{
            ImmutableUserProfile userProfile = parseUserProfile(req);
            userProfileService.insertUserProfile(userProfile);
            return buildPostResponse(userProfile.username());
        }
        catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return buildErrorResponse(e.getMessage(), true);
        }
    }

    @PutMapping("/update")
    public boolean updateUserProfile(@RequestBody ImmutableUserProfile userProfile){
        boolean update = false;
        try{
            userProfileService.updateUserProfile(userProfile);
            update = true;
        }
        catch (Exception e){
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
        }
        return delete;
    }
}
