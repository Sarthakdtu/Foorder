package com.foorder.dao.postgres;

public enum Table {

    USER_PROFILE("USER_PROFILE"),
    RESTAURANT("RESTAURANT"),
    PENDING_ORDERS("PENDING_ORDERS"),
    DELIVERED_ORDERS("DELIVERED_ORDERS"),
    CITY("CITY"),
    STREET("STREET");

    public final String name;

    private Table(String name) {
        this.name = name;
    }
}
