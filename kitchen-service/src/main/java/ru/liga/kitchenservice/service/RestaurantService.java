package ru.liga.kitchenservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.common.exceptions.NoSuchOrderException;
import ru.liga.kitchenservice.batisMapper.RestaurantMapper;
import ru.liga.common.entity.Restaurant;


@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantMapper restaurantMapper;

    public Restaurant getRestaurantById(long id) {
        return restaurantMapper.getRestaurantById(id);
    }
    public void saveRestaurant(Restaurant res) {
        restaurantMapper.saveRestaurant(res);
    }
    public void deleteRestaurant(long id) {
        Restaurant res = restaurantMapper.getRestaurantById(id);
        if(res == null) {
            throw new NoSuchOrderException("There is no restaurant with id " + id);
        }
        restaurantMapper.deleteRestaurantById(id);
    }
    public void changeRestaurantStatus(long id, String status) {
        restaurantMapper.updateRestaurantStatus(id, status);
    }
}
