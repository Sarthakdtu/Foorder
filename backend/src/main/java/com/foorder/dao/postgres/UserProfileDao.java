package com.foorder.dao.postgres;

import com.foorder.common.object.user.ImmutableUserProfile;
import com.foorder.model.UserProfile;

import java.util.List;

public interface UserProfileDao {
     List<ImmutableUserProfile> getAllUserProfiles();

     ImmutableUserProfile getUserProfileById(String userId);

     List<ImmutableUserProfile> getUserProfilesByCity(String cityName);

     List<ImmutableUserProfile> getUserProfilesByStreet(String streetName, String cityName);

     void insertUserProfile(ImmutableUserProfile userProfile);

     void updateUserProfile(ImmutableUserProfile userProfile);

     void deleteUserProfile(String userId);

     List<ImmutableUserProfile> getAllBots();
}
