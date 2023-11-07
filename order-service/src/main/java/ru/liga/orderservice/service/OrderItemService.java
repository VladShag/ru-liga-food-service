package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.dto.ItemToAddDTO;
import ru.liga.orderservice.dto.OrderToCreateDTO;
import ru.liga.common.entity.Order;
import ru.liga.common.entity.OrderItem;
import ru.liga.common.entity.RestaurantMenuItem;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.repository.OrderItemRepository;
import ru.liga.common.repository.RestaurantMenuItemRepository;

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
        for (ItemToAddDTO itemToAddDTO : dto.getMenuItems()) {
            OrderItem item = new OrderItem();
            item.setQuantity(itemToAddDTO.getQuantity());
            if (restaurantMenuItemRepository.getRestaurantMenuItemById(itemToAddDTO.getMenuItemId()).isPresent()) {
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

    public List<OrderItem> getOrderItemsByOrderId(long orderId) {
        List<OrderItem> items = repository.getOrderItemByOrder_Id(orderId);
        if (items.isEmpty()) {
            throw new NoSuchEntityException("There is no Items in Order with id: " + orderId);
        }
        return items;
    }

    public OrderItem getItemById(long id) {
        return repository.getOrderItemById(id).orElseThrow(() -> new NoSuchEntityException("There is no order item this id: " + id));
    }

    public void deleteItemById(long id) {
        repository.getOrderItemById(id).orElseThrow(() -> new NoSuchEntityException("There is no order item this id: " + id));
        repository.deleteById(id);
    }
}
