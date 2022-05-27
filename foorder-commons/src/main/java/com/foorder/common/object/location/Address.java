package com.foorder.common.object.location;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Address {
  public abstract City city();

  public abstract Street street();

  public abstract Block block();

  public abstract String houseNumber();
}
