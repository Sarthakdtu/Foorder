package com.foorder.controller.api;

import com.foorder.exceptions.DuplicateMenuItemException;
import com.foorder.exceptions.MenuItemsInvalidException;
import com.foorder.model.menu.MenuItem;
import com.foorder.service.MenuService;
import com.foorder.utils.LoggerService;
import com.foorder.utils.RandomStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("")
    public Object getHealth(){
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("alive", true);
        return result;
    }


    @GetMapping("/get")
    public List<MenuItem> getMenuItems(@RequestParam String restaurantId){
        List<MenuItem> items = null;
        try{
            items = menuService.getMenuItems(restaurantId);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return items;
    }

    @PostMapping("/add-items")
    public boolean addMenuItems(@RequestBody HashMap<String, Object> req) throws SQLException {
        boolean insert = false;
        try{
            String restaurantId = (String) req.get("restaurantId");
            List<HashMap<String, Object>> itemList = (List<HashMap<String, Object>>) req.get("items");
            List<MenuItem> menuItems = new ArrayList<>();
            HashMap<String, Boolean> coveredItems = new HashMap<>();
            for (HashMap<String, Object> item : itemList) {
                if(item.isEmpty() || !item.containsKey("name") || !item.containsKey("price") || !item.containsKey("timeToMake")){
                    throw new MenuItemsInvalidException();
                }
                String name = (String) item.get("name");
                Double price = (Double) item.get("price");
                Integer timeToMake = (Integer) item.get("timeToMake");

                if(!coveredItems.getOrDefault(name, false)){
                    coveredItems.put(name, true);
                    String id = RandomStrings.generateMenuItemId();
                    MenuItem tempItem = new MenuItem(id, name, price, timeToMake);
                    menuItems.add(tempItem);
                }
                else{
                    throw new DuplicateMenuItemException();
                }
            }
            menuService.addItems(restaurantId, menuItems);
            insert = true;
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
            e.printStackTrace();
        }
        return insert;
    }
}
