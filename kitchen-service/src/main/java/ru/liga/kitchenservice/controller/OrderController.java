package ru.liga.kitchenservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.Status;
import ru.liga.kitchenservice.dto.ChangeStatusDTO;
import ru.liga.kitchenservice.dto.OrderToKitchenDTO;
import ru.liga.kitchenservice.service.OrderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/kitchen")
public class OrderController {
    private final OrderService service;
    @GetMapping("/{id}")
    public OrderToKitchenDTO getOrderById(@PathVariable("id") long id) {
        return service.getOrderById(id);
    }


    @PostMapping("/status/{id}")
    public void setDeliveryStatus(@PathVariable("id") long id, @RequestBody ChangeStatusDTO dto) {
        Status status = dto.getOrderAction();
        service.setOrderStatus(id, status);
    }
}
