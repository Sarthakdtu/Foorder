package com.foorder.common.object.menu;

import org.immutables.value.Value;

@Value.Immutable
public abstract class MenuItem {

  public abstract String id();

  public abstract String name();

  public abstract Double price();

  public abstract Integer timeToMake(); // in seconds
}
