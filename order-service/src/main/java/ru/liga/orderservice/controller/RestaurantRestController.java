package ru.liga.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.orderservice.batisMapper.RestaurantMapper;
import ru.liga.orderservice.entity.Restaurant;

@RestController
@RequiredArgsConstructor
public class RestaurantRestController {

    private final RestaurantMapper service;

    @GetMapping("/restaurant/{id}")
    public Restaurant getRestaurantById(@PathVariable("id") long id) {
        return service.getRestaurantById(id);
    }
}
