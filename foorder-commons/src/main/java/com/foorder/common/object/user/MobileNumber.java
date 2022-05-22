package com.foorder.common.object.user;

import org.immutables.value.Value;

@Value.Immutable
public abstract class MobileNumber {
    public abstract String number();
}
