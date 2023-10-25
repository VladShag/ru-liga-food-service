package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.entity.RestaurantMenuItem;
import ru.liga.orderservice.exceptions.NoSuchOrderException;
import ru.liga.orderservice.repository.RestaurantMenuItemRepository;

@Service
@RequiredArgsConstructor
public class RestaurantMenuItemService {
    private final RestaurantMenuItemRepository repository;

    public RestaurantMenuItem getMenuItemById(long id) {
        return repository.getRestaurantMenuItemById(id).orElseThrow(() -> new NoSuchOrderException("There is no menu item this id: " + id));
    }

    public void deleteMenuItemById(long id) {
        repository.getRestaurantMenuItemById(id).orElseThrow(() -> new NoSuchOrderException("There is no menu item this id: " + id));
        repository.deleteById(id);

    }

    public RestaurantMenuItem changeItemPrice(long id, int price) {
        RestaurantMenuItem itemToChange = repository.getRestaurantMenuItemById(id).orElseThrow(() -> new NoSuchOrderException("There is no menu item this id: " + id));
        itemToChange.setPrice(price);
        repository.save(itemToChange);
        return itemToChange;
    }
}
