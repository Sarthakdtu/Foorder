package com.foorder.service;

import com.foorder.common.object.menu.ImmutableMenu;
import com.foorder.common.object.menu.ImmutableMenuItem;


import java.util.List;

public interface MenuService {
    public ImmutableMenu getMenuByRestaurantId(String restaurantId);
    public void insertMenu(ImmutableMenu menu);
    public void addItems(String restaurantId, List<ImmutableMenuItem> items);
    public void updateMenu(List<ImmutableMenuItem> items);
    public List<ImmutableMenuItem> getMenuItems(String restaurantId);
}
