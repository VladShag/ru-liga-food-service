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
        if(repository.getRestaurantMenuItemById(id).isPresent()) {
            return repository.getRestaurantMenuItemById(id).get();
        }
        else {
            throw exceptionById(id);
        }
    }
    public void deleteMenuItemById(long id) {
        if(repository.getRestaurantMenuItemById(id).isPresent()){
            repository.deleteById(id);
        } else {
            throw exceptionById(id);
        }

    }
    public RestaurantMenuItem changeItemPrice(long id, int price){
        if(repository.getRestaurantMenuItemById(id).isPresent()) {
            RestaurantMenuItem itemToChange = repository.getRestaurantMenuItemById(id).get();
            itemToChange.setPrice(price);
            repository.save(itemToChange);
            return itemToChange;
        }
        else {
            throw exceptionById(id);
        }
    }
    private static NoSuchOrderException exceptionById(long id) {
        return new NoSuchOrderException("There is no menu item this id " + id);
    }
}
