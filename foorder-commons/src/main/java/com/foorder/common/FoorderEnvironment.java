package com.foorder.common;

import com.foorder.common.object.order.ImmutableOrderWithTime;
import com.foorder.common.object.order.OrderStatus;
import com.foorder.common.object.restaurant.ImmutableBaseRestaurant;
import com.foorder.common.object.user.ImmutableBaseUser;
import com.foorder.common.property.KafkaProperty;
import java.time.LocalDateTime;
import java.util.UUID;

public class FoorderEnvironment {

  public static String getKafka(KafkaProperty property) {
    return System.getenv(property.name);
  }

  public static void main(String[] args) {
    ImmutableBaseRestaurant restaurant = ImmutableBaseRestaurant.builder().id("R1234").build();

    ImmutableBaseUser user = ImmutableBaseUser.builder().username("Sarthak").build();
    ImmutableOrderWithTime order =
        ImmutableOrderWithTime.builder()
            .id(UUID.randomUUID())
            .restaurant(restaurant)
            .user(user)
            .placedTime(LocalDateTime.now())
            .deliveredTime(null)
            .pickedUpTime(LocalDateTime.now())
            .status(OrderStatus.PICKEDUP)
            .build();
    System.out.println(order);
  }
}
