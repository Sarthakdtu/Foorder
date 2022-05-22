package com.foorder.service.impl;

import com.foorder.common.object.order.ImmutableBaseOrder;
import com.foorder.dao.postgres.DeliveredOrderDao;
import com.foorder.dao.postgres.PendingOrderDao;
import com.foorder.events.Event;
import com.foorder.events.OrderPlacedEvent;
import com.foorder.exceptions.*;
import com.foorder.model.*;
import com.foorder.model.eventObject.OrderPlacedEventObject;
import com.foorder.model.menu.MenuItem;
import com.foorder.model.order.*;
import com.foorder.service.*;
import com.foorder.utils.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    PendingOrderDao pendingOrderDao;

    @Autowired
    DeliveredOrderDao deliveredOrderDao;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MenuService menuService;

    @Autowired
    OrderListService orderListService;

    private void validateCity(String userCity, String restaurantCity) throws DifferentCityException {
        if(!userCity.equals(restaurantCity)){
            throw new DifferentCityException();
        }
    }

    private void validateNumberUniqueItems(List<OrderItem> items) throws UniqueOrderItemQtyExceeded {
        if(items.size() > 10){
            throw new UniqueOrderItemQtyExceeded();
        }
    }

    private void validateItemExistence(List<OrderItem> items, String restaurantId, HashMap<String, Integer> idTtmMap) throws OrderItemDoesNotExistException {
        LoggerService.info(String.valueOf(idTtmMap));
        for (OrderItem item: items) {
            if(idTtmMap.getOrDefault(item.getId(), 0) == 0){
                LoggerService.error("ItemId : " +item.getId());
                throw new OrderItemDoesNotExistException();
            }
        }
    }

    private void validateItemQuantity(List<OrderItem> items) throws OrderItemQtyException {
        for (OrderItem item: items) {
            if(item.getQuantity() > 10 || item.getQuantity() <1){
                throw new OrderItemQtyException();
            }
        }
    }

    @Override
    public HashMap<String, Object> validateOrder(ImmutableBaseOrder order, List<OrderItem> items, HashMap<String, Integer> idTtmMap) throws Exception {
        UserProfile userProfile = userProfileService.getUserProfileById(order.getUsername());
        Restaurant restaurant = restaurantService.getRestaurantById(order.getRestaurantId());
        validateCity(userProfile.getCityName(), restaurant.getCityName());
        validateNumberUniqueItems(items);
        validateItemExistence(items, order.getRestaurantId(), idTtmMap);
        validateItemQuantity(items);
        HashMap<String, Object> result = new HashMap<>();
        result.put("restaurant", restaurant);
        result.put("userProfile", userProfile);
        return result;
    }

    private List<OrderItemWithName> createOrderItemWithNameList(List<OrderItem> orderItems, List<MenuItem> menuItems){
        HashMap<String, String> idNameMap = this.getMenuItemIdNamemMap(menuItems);
        List<OrderItemWithName> orderItemWithNameList = new ArrayList<>();
        for(OrderItem item : orderItems){
            OrderItemWithName orderItemWithName = new OrderItemWithName(item, idNameMap.get(item.getId()));
            orderItemWithNameList.add(orderItemWithName);
        }
        return orderItemWithNameList;
    }

    @Override
    public Order getOrderById(String id) {
        return pendingOrderDao.getOrderById(id);
    }

    @Override
    public DetailedOrder getOrderDetailsById(String id) {
        Order order = pendingOrderDao.getOrderById(id);
        String restaurantName = restaurantService.getRestaurantById(order.getRestaurantId()).getName();
        List<MenuItem> menuItems = menuService.getMenuItems(order.getRestaurantId());
        LoggerService.info("Menu for restaurantId " + order.getRestaurantId() + " fetched");

        OrderList orderList = orderListService.getOrderListById(order.getId());
        List<OrderItem> orderItems = orderList.getItems();
        LoggerService.info("Got order items for id " + order.getId());
        List<OrderItemWithName> orderItemWithNameList = this.createOrderItemWithNameList(orderItems, menuItems);
        return new DetailedOrder(order, restaurantName, orderItemWithNameList);
    }

    private HashMap<String, Integer> getMenuItemIdTtmMap(List<MenuItem> menuItems) throws MenuDoestNotExistException {
        if(menuItems == null){
            throw new MenuDoestNotExistException();
        }
        HashMap<String, Integer> idTtmMap = new HashMap<>();
        for (MenuItem item: menuItems) {
            idTtmMap.put(item.getId(), item.getTimeToMake());
        }
        return idTtmMap;
    }

    private LocalDateTime getTimeToPrepare(LocalDateTime currentTime, HashMap<String, Integer> idTtmMap, List<OrderItem> items){
        int timeToPrepare = 0;
        for(OrderItem item : items){
            timeToPrepare += (item.getQuantity()*idTtmMap.get(item.getId()));
        }
        return currentTime.plusSeconds(timeToPrepare);
    }

    private void createOrderList(String orderId, List<OrderItem> items){
        OrderList orderList = new OrderList(orderId, items);
        orderListService.insertOrderList(orderList);
    }

    private HashMap<String, Double> getMenuItemIdPricemMap(List<MenuItem> menuItems) {
        HashMap<String, Double> idPriceMap = new HashMap<>();
        for (MenuItem item: menuItems) {
            idPriceMap.put(item.getId(), item.getPrice());
        }
        return idPriceMap;
    }

    private HashMap<String, String> getMenuItemIdNamemMap(List<MenuItem> menuItems) {
        HashMap<String, String> idNameMap = new HashMap<>();
        for (MenuItem item: menuItems) {
            idNameMap.put(item.getId(), item.getName());
        }
        return idNameMap;
    }

    private Double calculateOrderPrice(HashMap<String, Double> idPriceMap, List<OrderItem> items){
        double price = 0;
        for(OrderItem item : items){
            price += (item.getQuantity()*idPriceMap.get(item.getId()));
        }
        return price;
    }

    @Override
    public void insertPendingOrder(PendingOrder order, List<OrderItem> items) throws Exception {
        List<MenuItem> menuItems = menuService.getMenuItems(order.getRestaurantId());
        HashMap<String, Integer> idTtmMap = this.getMenuItemIdTtmMap(menuItems);
        HashMap<String, Double> idPriceMap = this.getMenuItemIdPricemMap(menuItems);

        HashMap<String, Object> result = this.validateOrder(order, items, idTtmMap);
        LoggerService.info("Validation successful");

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime pickUpTime = this.getTimeToPrepare(currentTime, idTtmMap, items);
        Double price = calculateOrderPrice(idPriceMap, items);
        LoggerService.info("Order created at " + currentTime + " and will be picked up at " + pickUpTime);

        order.setOrderTime(currentTime);
        order.setPickUpTime(pickUpTime);
        order.setPrice(price);
        this.createOrderList(order.getId(), items);
        LoggerService.info("OrderList created for orderId " + order.getId());

        pendingOrderDao.insertPendingOrder(order);
        LoggerService.info("Order Placed : " + order);
        UserProfile userProfile = (UserProfile) result.get("userProfile");
        Restaurant restaurant = (Restaurant) result.get("restaurant");
        List<OrderItemWithName> orderItemWithNameList = this.createOrderItemWithNameList(items, menuItems);
        OrderPlacedEventObject orderPlacedEventObject = OrderPlacedEventObject.builder()
                        .orderId(order.getId())
                        .mobileNumber(userProfile.getMobileNumber())
                        .restaurantName(restaurant.getName())
                        .username(userProfile.getUsername())
                        .restaurantId(restaurant.getId())
                        .orderItemWithNameList(orderItemWithNameList)
                        .deliveryHouseNumber(userProfile.getHouseNumber())
                        .pickUpTime(order.getPickUpTime())
                        .deliveryStreetName(userProfile.getStreetName())
                        .cityName(restaurant.getCityName())
                        .restaurantStreetName(restaurant.getStreetName())
                        .build();
        Event orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setMessage(orderPlacedEventObject.toJson());
        orderPlacedEvent.produce();
        //TODO: save order in user_profile history
        //TODO: save order in restaurant history
    }

    @Override
    public void deletePendingOrder(String orderId) {

    }

    @Override
    public void insertDeliveredOrder(Order order) {

    }

    @Override
    public void deleteDeliveredOrder(String orderId) {

    }

    @Override
    public List<Order> getAllPendingOrdersByRestaurantId(String restaurantId) {
        return null;
    }

    @Override
    public List<Order> getAllDeliveredOrdersByRestaurantId(String restaurantId) {
        return null;
    }

    @Override
    public List<Order> getOrderHistoryByUser(String username) {
        return null;
    }

    @Override
    public List<Order> getOrderHistoryByRestaurant(String restaurantId) {
        return null;
    }


    @Override
    public List<Order> getAllPendingOrderByRestaurantId(String restaurantId) {
        return null;
    }

    @Override
    public List<Order> getAllPendingOrderByUsername(String username) {
        return null;
    }
}
