package ru.liga.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.entity.RestaurantMenuItem;
import ru.liga.orderservice.service.RestaurantMenuItemService;

@RestController
@RequiredArgsConstructor
public class RestaurantMenuItemRestController {
    private final RestaurantMenuItemService service;
    @GetMapping("/resItem/{id}")
    public RestaurantMenuItem getRestaurantItemById(@PathVariable("id") long id) {
        return service.getMenuItemById(id);
    }

    @DeleteMapping("/resItem/{id}")
    public void deleteOrderItemById(@PathVariable("id") long id) {
        service.deleteMenuItemById(id);
    }
    @PutMapping("/resItem/{id}")
    public RestaurantMenuItem changeResItemPrice(@PathVariable("id") long id, @RequestParam int price) {
        return service.changeItemPrice(id,price);
    }
}
