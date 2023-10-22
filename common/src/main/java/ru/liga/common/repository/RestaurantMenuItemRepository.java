package ru.liga.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entity.RestaurantMenuItem;


import java.util.Optional;

@Repository
public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {
    Optional<RestaurantMenuItem> getRestaurantMenuItemById(long id);
}
