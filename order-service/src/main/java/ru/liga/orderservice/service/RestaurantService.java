package ru.liga.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.batisMapper.RestaurantMapper;
import ru.liga.orderservice.entity.Restaurant;


@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantMapper restaurantMapper;

    public Restaurant getRestaurantById(long id) {
        return restaurantMapper.getRestaurantById(id);
    }
}
