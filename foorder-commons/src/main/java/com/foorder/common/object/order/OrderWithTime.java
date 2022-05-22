package com.foorder.common.object.order;

import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

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
