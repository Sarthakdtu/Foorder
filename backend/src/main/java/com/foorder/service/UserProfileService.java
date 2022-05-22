package com.foorder.service;

import com.foorder.common.object.user.ImmutableUserProfile;

import java.util.List;

public interface UserProfileService {
     List<ImmutableUserProfile> getAllUserProfiles();

     ImmutableUserProfile getUserProfileById(String id);

     List<ImmutableUserProfile> getUserProfilesByCity(String cityName);

     List<ImmutableUserProfile> getUserProfilesByStreet(String streetName, String cityName);

     void insertUserProfile(ImmutableUserProfile userProfile);

     void updateUserProfile(ImmutableUserProfile userProfile);

     void deleteUserProfile(String id);

     List<ImmutableUserProfile> getAllBots();

}
