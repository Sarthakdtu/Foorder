package com.foorder.common.object.order;

import com.foorder.common.object.restaurant.BaseRestaurant;
import com.foorder.common.object.user.BaseUser;
import org.immutables.value.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value.Immutable
public abstract class BaseOrder {
    public abstract UUID id();
    public abstract BaseRestaurant restaurant();
    public abstract BaseUser user();
    public abstract OrderStatus status();
}
