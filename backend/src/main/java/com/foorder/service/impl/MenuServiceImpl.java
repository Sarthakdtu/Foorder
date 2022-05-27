package com.foorder.service.impl;

import com.foorder.common.object.menu.ImmutableMenu;
import com.foorder.common.object.menu.ImmutableMenuItem;
import com.foorder.dao.mongodb.MenuRepository;

import com.foorder.service.MenuService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    MenuRepository menuRepository;

    @Override
    public ImmutableMenu getMenuByRestaurantId(String restaurantId) {
        return menuRepository.findMenuByRestaurantId(restaurantId);
    }

    @Override
    public void insertMenu(ImmutableMenu menu) {
        menuRepository.save(menu);
    }

    @Override
    public void addItems(String restaurantId, List<ImmutableMenuItem> items) {
        ImmutableMenu menu = menuRepository.findMenuByRestaurantId(restaurantId);
        if(menu.items() != null){
            List<ImmutableMenuItem> prevItems = menu.items();
            items.addAll(prevItems);
        }
        menu.withItems(items);
        menuRepository.save(menu);
        logger.info("Update menu with restaurantId {}", restaurantId);
    }

    @Override
    public void updateMenu(List<ImmutableMenuItem> items) {

    }

    @Override
    public List<ImmutableMenuItem> getMenuItems(String restaurantId) {
       return menuRepository.findMenuByRestaurantId(restaurantId).items();
    }
}
