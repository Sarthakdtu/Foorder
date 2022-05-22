package com.foorder.common.object.user;
import org.immutables.value.Value;

@Value.Immutable
public abstract class BaseUser {
    public abstract String username();
}
