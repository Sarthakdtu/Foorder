package com.foorder.common.object.order;

public enum OrderStatus {
  PLACED("placed"),
  PREPARED("prepared"),
  PICKEDUP("picked-up"),
  DELIVERED("delivered");

  String name;

  OrderStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
