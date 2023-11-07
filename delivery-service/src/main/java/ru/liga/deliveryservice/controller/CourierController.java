package ru.liga.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.common.entity.Courier;
import ru.liga.deliveryservice.dto.ChangeCouriersStatusDTO;
import ru.liga.deliveryservice.service.CourierService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courier")
public class CourierController {
    private final CourierService service;

    @GetMapping("/")
    public List<Courier> getAllCouriers() {
        return service.getAllCouriers();
    }

    @GetMapping("/{id}")
    public Courier getCourierById(@PathVariable("id") @Min(0) long id) {
        return service.getCourierById(id);
    }

    @PostMapping("/")
    public Courier saveCourier(@RequestBody Courier courier) {
        return service.saveCourier(courier);
    }

    @PutMapping("/{id}")
    public Courier setCourierStatus(@PathVariable("id") @Min(0) long id, @RequestBody ChangeCouriersStatusDTO status) {
        return service.setCourierStatus(id, status.getCourierStatus().toString());
    }

    @DeleteMapping("/{id}")
    public String deleteCourier(@PathVariable("id") @Min(0) long id) {
        return service.deleteCourier(id);
    }
}
