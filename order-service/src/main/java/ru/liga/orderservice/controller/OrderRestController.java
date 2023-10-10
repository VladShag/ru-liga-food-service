package ru.liga.orderservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.entity.Order;
import ru.liga.orderservice.service.OrderService;

import java.util.List;

@RestController
public class OrderRestController {
    private final OrderService service = new OrderService();
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }
    @GetMapping("/order/{id}")
    public Order getOrderById(@PathVariable("id") long id) {
        return service.getOrderById(id);
    }
    @GetMapping("/order")
    public List<Order> getOrdersByStatus(@RequestParam("status") String status){
        return service.getOrdersByStatus(status);
    }
    @PostMapping("/order")
    public Order addNewOrder(@RequestBody Order order) {
        return service.addNewOrder(order);
    }
    @PutMapping("/order/{id}")
    public Order changeOrderInfo(@PathVariable("id") long id, @RequestBody Order order) {
        return service.changeOrderInfo(id, order);
    }
}
