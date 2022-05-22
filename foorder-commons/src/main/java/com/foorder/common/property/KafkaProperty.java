package com.foorder.common.property;

public enum KafkaProperty {
    BROKER_URL("BROKER_URL"),
    USERNAME("USERNAME"),
    PASSWORD("PASSWORD");

    public final String name;

    KafkaProperty(String name){
        this.name = name;
    }
}
