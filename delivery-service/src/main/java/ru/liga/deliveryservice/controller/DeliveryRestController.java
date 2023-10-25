package ru.liga.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.deliveryservice.dto.ChangeStatusDTO;
import ru.liga.deliveryservice.dto.DelieveryListDTO;
import ru.liga.deliveryservice.dto.DeliveryDTO;
import ru.liga.deliveryservice.dto.FullOrderDTO;
import ru.liga.deliveryservice.entity.Status;
import ru.liga.deliveryservice.feign.CoreFeign;
import ru.liga.deliveryservice.service.DeliveryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryRestController {
    private final DeliveryService service;
    private final CoreFeign feign;

    @GetMapping("/{id}")
    public DeliveryDTO getDeliveryById(@PathVariable("id") long id) {
        return service.getDeliveryById(id);
    }

    @GetMapping("/")
    public DelieveryListDTO getDeliveriesByStatus(@RequestParam("status") String status) {
        return service.getDeliveriesByStatus(status);
    }

    @PostMapping("/{id}")
    public DeliveryDTO setDeliveryStatus(@PathVariable("id") long id, @RequestBody ChangeStatusDTO dto) {
        Status status = dto.getOrderAction();
        feign.getData(id);
        return service.setDeliveryStatus(id, status);
    }
}
