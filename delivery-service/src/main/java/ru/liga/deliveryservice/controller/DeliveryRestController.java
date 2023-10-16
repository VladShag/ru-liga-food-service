package ru.liga.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.deliveryservice.entity.Delivery;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeliveryRestController {
    private final DeliveryService service;

    @GetMapping("/delivery/{id}")
    public Delivery getDeliveryById(@PathVariable("id") long id) {
        return service.getDeliveryById(id);
    }
    @PostMapping("/delivery")
    public Delivery addNewDelivery(@RequestBody Delivery delivery) {
        return service.addNewDelivery(delivery);
    }
    @GetMapping("/delivery")
    public List<Delivery> getDeliveriesByStatus(@RequestParam("status") String status) {
        return service.getDeliveriesByStatus(status);
    }
}
