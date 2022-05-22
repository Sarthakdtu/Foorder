package com.foorder.service;

import com.foorder.model.menu.Menu;
import com.foorder.model.menu.MenuItem;

import java.util.List;

public interface MenuService {
    public Menu getMenuByRestaurantId(String restaurantId);
    public void insertMenu(Menu menu);
    public void addItems(String restaurantId, List<MenuItem> items);
    public void updateMenu(List<MenuItem> items);
    public List<MenuItem> getMenuItems(String restaurantId);
}
