package com.foorder.common.object.order;

import java.time.LocalDateTime;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
public abstract class OrderWithTime extends BaseOrder {

  public abstract LocalDateTime placedTime();

  @Nullable
  public abstract LocalDateTime preparedTime();

  @Nullable
  public abstract LocalDateTime pickedUpTime();

  @Nullable
  public abstract LocalDateTime deliveredTime();
}
