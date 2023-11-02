package ru.liga.kitchenservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.RestaurantMenuItem;
import ru.liga.kitchenservice.service.RestaurantMenuItemService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/res-menu-items")
public class RestaurantMenuItemRestController {
    private final RestaurantMenuItemService service;
    @GetMapping("/")
    public List<RestaurantMenuItem> getAllMenuItems() {
        return service.getAllMenuItems();
    }
    @GetMapping("/restaurant/{id}")
    public List<RestaurantMenuItem> getAllMenuItemsByRestaurant(@PathVariable("id") @Min(0) long resId) {
        return service.getAllMenuItemsByRestaurant(resId);
    }
    @PostMapping("/")
    public RestaurantMenuItem saveNewMenuItem(@RequestBody RestaurantMenuItem item) {
        return service.saveMenuItem(item);
    }
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
        return service.changeItemPrice(id, price);
    }
}
