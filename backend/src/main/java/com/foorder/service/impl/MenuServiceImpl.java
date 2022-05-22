package com.foorder.service.impl;

import com.foorder.dao.mongodb.MenuRepository;
import com.foorder.model.menu.Menu;
import com.foorder.model.menu.MenuItem;
import com.foorder.service.MenuService;
import com.foorder.utils.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Override
    public Menu getMenuByRestaurantId(String restaurantId) {
        return menuRepository.findMenuByRestaurantId(restaurantId);
    }

    @Override
    public void insertMenu(Menu menu) {
        menuRepository.save(menu);
    }

    @Override
    public void addItems(String restaurantId, List<MenuItem> items) {
        Menu menu = menuRepository.findMenuByRestaurantId(restaurantId);
        if(menu.getItems() != null){
            List<MenuItem> prevItems = menu.getItems();
            items.addAll(prevItems);
        }
        menu.setItems(items);
        LoggerService.info(menu.toString());
        menuRepository.save(menu);
    }

    @Override
    public void updateMenu(List<MenuItem> items) {

    }

    @Override
    public List<MenuItem> getMenuItems(String restaurantId) {
        Menu menu = menuRepository.findMenuByRestaurantId(restaurantId);
        return menu.getItems();
    }
}
