package ru.liga.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.orderservice.dto.*;
import ru.liga.common.entity.Status;
import ru.liga.orderservice.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderRestController {
    private final OrderService service;

    @GetMapping("/")
    public MainOrderListDTO getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public FullOrderDTO getOrderById(@PathVariable("id") long id) {
        return service.getOrderById(id);
    }

    @GetMapping("/orderByStatus")
    public MainOrderListDTO getOrdersByStatus(@RequestParam("status") Status status) {
        return service.getOrdersByStatus(status);
    }

    @PostMapping("/")
    public OrderCreatedDTO addNewOrder(@RequestBody OrderToCreateDTO dto) {
        return service.addNewOrder(dto);
    }

    @PostMapping("/status/{id}")
    public FullOrderDTO setDeliveryStatus(@PathVariable("id") long id, @RequestBody ChangeStatusDTO dto) {
        Status status = dto.getOrderAction();
        service.setOrderStatus(id, status);
        return service.getOrderById(id);
    }

}
