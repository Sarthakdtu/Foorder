package com.foorder.dao.mongodb;

import com.foorder.common.object.menu.ImmutableMenu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;



public interface MenuRepository extends MongoRepository<ImmutableMenu, String> {

    @Query("{_id:'?0'}")
    ImmutableMenu findMenuByRestaurantId(String restaurantId);

    long count();
}
