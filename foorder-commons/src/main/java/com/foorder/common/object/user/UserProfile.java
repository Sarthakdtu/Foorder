package com.foorder.common.object.user;

import com.foorder.common.object.location.Address;
import org.immutables.value.Value;

@Value.Immutable
public abstract class UserProfile extends BaseUser {

  public abstract Address address();

  public abstract MobileNumber mobileNumber();

  public abstract boolean isBot();
}
