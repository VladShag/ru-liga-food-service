package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.liga.orderservice.dto.ItemToAddDTO;
import ru.liga.orderservice.dto.OrderToCreateDTO;
import ru.liga.orderservice.entity.Order;
import ru.liga.orderservice.entity.OrderItem;
import ru.liga.orderservice.entity.RestaurantMenuItem;
import ru.liga.orderservice.exceptions.NoSuchOrderException;
import ru.liga.orderservice.repository.OrderItemRepository;
import ru.liga.orderservice.repository.RestaurantMenuItemRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository repository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    public List<OrderItem> addNewOrderItems(@Valid OrderToCreateDTO dto, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for(ItemToAddDTO itemToAddDTO : dto.getMenuItems()) {
            OrderItem item = new OrderItem();
            item.setQuantity(itemToAddDTO.getQuantity());
            if(restaurantMenuItemRepository.getRestaurantMenuItemById(itemToAddDTO.getMenuItemId()).isPresent()) {
                RestaurantMenuItem resItem = restaurantMenuItemRepository.getRestaurantMenuItemById(itemToAddDTO.getMenuItemId()).get();
                item.setRestaurantMenuItem(resItem);
                item.setPrice(resItem.getPrice()
                        * itemToAddDTO.getQuantity());
                item.setOrder(order);
                orderItems.add(item);
                repository.save(item);
            }
        }
        return orderItems;
    }
    public OrderItem getItemById(long id){
        return repository.getOrderItemById(id).orElseThrow(() -> new NoSuchOrderException("There is no order item this id: " + id));
    }
    public void deleteItemById(long id) {
        repository.getOrderItemById(id).orElseThrow(() -> new NoSuchOrderException("There is no order item this id: " + id));
        repository.deleteById(id);
    }
}
