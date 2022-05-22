package com.foorder.service.impl;

import com.foorder.common.object.user.ImmutableBaseUser;
import com.foorder.common.object.user.ImmutableUserProfile;
import com.foorder.dao.postgres.UserProfileDao;
import com.foorder.model.UserProfile;
import com.foorder.model.location.Street;
import com.foorder.service.StreetService;
import com.foorder.service.UserProfileService;
import com.foorder.utils.RandomStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    UserProfileDao userProfileDao;

    @Autowired
    StreetService streetService;
    @Override
    public List<ImmutableUserProfile> getAllUserProfiles() {
        return userProfileDao.getAllUserProfiles();
    }

    @Override
    public ImmutableUserProfile getUserProfileById(String id) {
        return userProfileDao.getUserProfileById(id);
    }

    @Override
    public List<ImmutableUserProfile> getUserProfilesByCity(String cityName) {
        return userProfileDao.getUserProfilesByCity(cityName);
    }

    @Override
    public List<ImmutableUserProfile> getUserProfilesByStreet(String streetName, String cityName) {
        return userProfileDao.getUserProfilesByStreet(streetName, cityName);
    }

    @Override
    public void insertUserProfile(ImmutableUserProfile userProfile) {
        Street street = new Street(userProfile.getStreetName(),
                                    userProfile.getCityName(),
                                    RandomStrings.generatePincode());
        streetService.insertStreet(street);
        userProfileDao.insertUserProfile(userProfile);
    }

    @Override
    public void updateUserProfile(ImmutableUserProfile userProfile) {
        userProfileDao.updateUserProfile(userProfile);
    }

    @Override
    public void deleteUserProfile(String username) {
        userProfileDao.deleteUserProfile(username);
    }

    @Override
    public List<ImmutableUserProfile> getAllBots() {
        return userProfileDao.getAllBots();
    }
}
