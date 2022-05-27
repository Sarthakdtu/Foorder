package com.foorder.common.object.menu;

import java.util.List;
import org.immutables.value.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value.Immutable
@Document("menu")
interface Menu {

  @Id
  String restaurantId();

  List<ImmutableMenuItem> items();
}
