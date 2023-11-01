package ru.liga.kitchenservice.batisMapper;

import org.springframework.stereotype.Repository;
import ru.liga.common.entity.Restaurant;

import java.util.List;

@Repository
public interface RestaurantMapper {
        void saveRestaurant(Restaurant restaurant);
        Restaurant getRestaurantById(long id);

        void deleteRestaurantById(long id);

        void updateRestaurantStatus(long id, String status);

}
