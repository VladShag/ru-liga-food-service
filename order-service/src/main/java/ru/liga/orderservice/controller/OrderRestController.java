package ru.liga.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.FullOrderDTO;
import ru.liga.orderservice.dto.MainOrderListDTO;
import ru.liga.orderservice.dto.OrderCreatedDTO;
import ru.liga.orderservice.dto.OrderToCreateDTO;
import ru.liga.orderservice.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService service;
    @GetMapping("/orders")
    public MainOrderListDTO getAllOrders() {
        return service.getAllOrders();
    }
    @GetMapping("/order/{id}")
    public FullOrderDTO getOrderById(@PathVariable("id") long id) {
        return service.getOrderById(id);
    }
    @GetMapping("/order")
    public MainOrderListDTO getOrdersByStatus(@RequestParam("status") String status){
        return service.getOrdersByStatus(status);
    }
    @PostMapping("/order")
    public OrderCreatedDTO addNewOrder(@RequestBody OrderToCreateDTO dto) {
        return service.addNewOrder(dto);
    }

}
