package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.ItemToAddDTO;
import ru.liga.orderservice.dto.OrderToCreateDTO;
import ru.liga.orderservice.entity.Order;
import ru.liga.orderservice.entity.OrderItem;
import ru.liga.orderservice.repository.OrderItemRepository;
import ru.liga.orderservice.repository.RestaurantMenuItemRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository repository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    public List<OrderItem> addNewOrderItems(OrderToCreateDTO dto, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for(ItemToAddDTO i : dto.getMenuItems()) {
            OrderItem item = new OrderItem();
            item.setQuantity(i.getQuantity());
            item.setRestaurantMenuItem(restaurantMenuItemRepository.getRestaurantMenuItemById(i.getMenuItemId()));
            item.setPrice(restaurantMenuItemRepository.getRestaurantMenuItemById(i.getMenuItemId()).getPrice()
                    * i.getQuantity());
            item.setOrder(order);
            orderItems.add(item);
            repository.save(item);
        }
        return orderItems;
    }
    public OrderItem getItemById(long id){
        return repository.getOrderItemById(id);
    }
    public void deleteItemById(long id) {
        repository.deleteById(id);
    }
}
