package ru.liga.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.orderservice.entity.RestaurantMenuItem;
@Repository
public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {
    RestaurantMenuItem getRestaurantMenuItemById(long id);
}
