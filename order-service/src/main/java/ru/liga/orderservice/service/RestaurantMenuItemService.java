package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.entity.RestaurantMenuItem;
import ru.liga.orderservice.repository.RestaurantMenuItemRepository;
@Service
@RequiredArgsConstructor
public class RestaurantMenuItemService {
    private final RestaurantMenuItemRepository repository;
    public RestaurantMenuItem getMenuItemById(long id) {
        return repository.getRestaurantMenuItemById(id);
    }
    public void deleteMenuItemById(long id) {
        repository.deleteById(id);
    }
    public RestaurantMenuItem changeItemPrice(long id, int price){
        RestaurantMenuItem itemToChange = repository.getRestaurantMenuItemById(id);
        itemToChange.setPrice(price);
        repository.save(itemToChange);
        return itemToChange;
    }
}
