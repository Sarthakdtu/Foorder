package com.foorder.common.object.restaurant;

import com.foorder.common.object.location.Address;
import org.immutables.value.Value;

@Value.Immutable
public abstract class Restaurant extends BaseRestaurant {
  public abstract String name();

  public abstract Address address();
}
