package com.foorder.common.object.restaurant;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public abstract class BaseRestaurant {
    public abstract String id();
}
