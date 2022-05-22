package com.foorder.dao.mongodb;

import com.foorder.model.menu.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MenuRepository extends MongoRepository<Menu, String> {

    @Query("{_id:'?0'}")
    Menu findMenuByRestaurantId(String restaurantId);

    public long count();
}
