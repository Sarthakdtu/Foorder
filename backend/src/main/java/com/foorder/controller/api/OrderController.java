package com.foorder.controller.api;

import com.foorder.exceptions.OrderItemInvalidException;
import com.foorder.model.order.Order;
import com.foorder.model.order.OrderItem;
import com.foorder.model.order.PendingOrder;
import com.foorder.service.OrderService;
import com.foorder.utils.LoggerService;
import com.foorder.utils.RandomStrings;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("")
    public Object getHealth(){
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("alive", true);
        return result;
    }

    @GetMapping("/get")
    public Order getOrderDetailsById(@RequestParam String id) throws SQLException, JSONException {
        Order order = null;
        try{
            order = orderService.getOrderDetailsById(id);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
            e.printStackTrace();
        }
        return order;
    }

    @GetMapping("/get-basic")
    public Order getOrderById(@RequestParam String id) throws SQLException, JSONException {
        Order order = null;
        try{
            order = orderService.getOrderById(id);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
            e.printStackTrace();
        }
        return order;
    }

    @GetMapping("/restaurant")
    public List<Order> getAllPendingOrderByRestaurantId(@RequestParam String restaurantId) throws SQLException, JSONException {
        List<Order> orders = null;
        try{
            orders = orderService.getAllPendingOrderByRestaurantId(restaurantId);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    @GetMapping("/user")
    public List<Order> getAllPendingOrderByUsername(@RequestParam String username) throws SQLException, JSONException {
        List<Order> orders = null;
        try{
            orders = orderService.getAllPendingOrderByUsername(username);
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    @PostMapping("/create")
    public HashMap<String, String> createOrder(@RequestBody HashMap<String, Object> req) throws Exception {

        boolean ordered = true;
        String id = RandomStrings.generateOrderId();
        try{
            String username = (String) req.get("username");
            String restaurantId = (String) req.get("restaurantId");
            List<HashMap<String, Object>> itemList = (List<HashMap<String, Object>>) req.get("items");
            List<OrderItem> orderItems = new ArrayList<>();
            HashMap<String, Integer> coveredItems = new HashMap<>();
            for (HashMap<String, Object> item : itemList) {
                if(item.isEmpty() || !item.containsKey("id") || !item.containsKey("quantity")){
                    throw new OrderItemInvalidException();
                }
                String itemId = (String) item.get("id");
                Integer quantity = (Integer) item.get("quantity");
                Integer oldQty = coveredItems.getOrDefault(itemId, 0);
                if(oldQty == 0){
                    coveredItems.put(itemId, quantity);
                }
                else{
                    coveredItems.put(itemId, oldQty + quantity);
                }
            }

            for (Map.Entry<String, Integer> entry : coveredItems.entrySet()) {
                OrderItem tempItem = new OrderItem(entry.getKey(), entry.getValue());
                orderItems.add(tempItem);
            }

            PendingOrder order = new PendingOrder(id, username, restaurantId);
            orderService.insertPendingOrder(order, orderItems);
        }
        catch (Exception e){
            ordered = false;
            LoggerService.error(e.getMessage());
            e.printStackTrace();
        }
        if(!ordered){
            id = null;
        }
        HashMap<String, String> res = new HashMap<>();
        res.put("id", id);
        res.put("success", String.valueOf(ordered));
        return res;
    }

    @DeleteMapping("/cancel")
    public boolean cancelOrder(@RequestBody HashMap<String, String> req){
        String id = req.get("id");
        boolean delete = false;
        try{
            orderService.deletePendingOrder(id);
            delete = true;
        }
        catch (Exception e){
            LoggerService.error(e.getMessage());
        }
        return delete;
    }
}
