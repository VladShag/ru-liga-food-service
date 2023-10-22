package ru.liga.orderservice.batisMapper;

import org.springframework.stereotype.Repository;
import ru.liga.orderservice.entity.Restaurant;


public interface RestaurantMapper {
        Restaurant getRestaurantById(long id);

        void deleteRestaurantById(long id);

}
