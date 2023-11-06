package ru.liga.kitchenservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.entity.RestaurantMenuItem;
import ru.liga.common.exceptions.NoSuchEntityException;
import ru.liga.common.repository.RestaurantMenuItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantMenuItemService {
    private final RestaurantMenuItemRepository repository;

    public List<RestaurantMenuItem> getAllMenuItems() {
        List<RestaurantMenuItem> items = repository.findAll();
        if (items.isEmpty()) {
            throw new NoSuchEntityException("Res.Menu Item is empty!");
        }
        return items;
    }

    public List<RestaurantMenuItem> getAllMenuItemsByRestaurant(long resId) {
        List<RestaurantMenuItem> resMenu = repository.getRestaurantMenuItemByRestaurantId(resId);
        if (resMenu.isEmpty()) {
            throw new NoSuchEntityException("Menu of restaurant with id " + resId + " is empty!");
        }
        return resMenu;
    }

    public RestaurantMenuItem saveMenuItem(RestaurantMenuItem item) {
        repository.save(item);
        return item;
    }

    public RestaurantMenuItem getMenuItemById(long id) {
        return repository.getRestaurantMenuItemById(id).orElseThrow(() -> new NoSuchEntityException("There is no menu item this id: " + id));
    }

    public void deleteMenuItemById(long id) {
        repository.getRestaurantMenuItemById(id).orElseThrow(() -> new NoSuchEntityException("There is no menu item this id: " + id));
        repository.deleteById(id);

    }

    public RestaurantMenuItem changeItemPrice(long id, int price) {
        RestaurantMenuItem itemToChange = repository.getRestaurantMenuItemById(id).orElseThrow(() -> new NoSuchEntityException("There is no menu item this id: " + id));
        itemToChange.setPrice(price);
        repository.save(itemToChange);
        return itemToChange;
    }
}
