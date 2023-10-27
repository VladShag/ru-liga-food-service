package ru.liga.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.common.entity.OrderItem;
import ru.liga.orderservice.service.OrderItemService;

@RestController
@RequiredArgsConstructor
public class OrderItemRestController {
    private final OrderItemService service;

    @GetMapping("/orderItem/{id}")
    public OrderItem getOrderItemById(@PathVariable("id") long id) {
        return service.getItemById(id);
    }

    @DeleteMapping("/orderItem/{id}")
    public void deleteOrderItemById(@PathVariable("id") long id) {
        service.deleteItemById(id);
    }
}
