package com.foorder.controller.api;

import com.foorder.common.object.menu.ImmutableMenuItem;
import com.foorder.exceptions.DuplicateMenuItemException;
import com.foorder.exceptions.MenuItemsInvalidException;

import com.foorder.service.MenuService;
import com.foorder.utils.RandomStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.foorder.utils.ResponseBuilder.buildGetResponse;

@RestController
@RequestMapping("api/v1/menu")
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    MenuService menuService;

    @GetMapping("")
    public ResponseEntity<?> getHealth(){
        return buildGetResponse("alive");
    }


    @GetMapping("/get")
    public List<ImmutableMenuItem> getMenuItems(@RequestParam String restaurantId){
        List<ImmutableMenuItem> items = null;
        try{
            items = menuService.getMenuItems(restaurantId);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return items;
    }

    @PostMapping("/add-items")
    public boolean addMenuItems(@RequestBody HashMap<String, Object> req) throws SQLException {
        boolean insert = false;
        try{
            String restaurantId = (String) req.get("restaurantId");
            List<HashMap<String, Object>> itemList = (List<HashMap<String, Object>>) req.get("items");
            List<ImmutableMenuItem> menuItems = new ArrayList<>();
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
                    ImmutableMenuItem tempItem =ImmutableMenuItem.builder()
                                                .id(id)
                                                .name(name)
                                                .price(price)
                                                .timeToMake(timeToMake)
                                                .build();
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
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return insert;
    }
}
