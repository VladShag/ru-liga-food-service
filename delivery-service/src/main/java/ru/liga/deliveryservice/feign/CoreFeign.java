package ru.liga.deliveryservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.deliveryservice.dto.ChangeStatusDTO;
import ru.liga.deliveryservice.dto.FullOrderDTO;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@FeignClient(name = "order-service", url = "http://localhost:9001")
public interface CoreFeign {
    @GetMapping("/order/{id}")
    FullOrderDTO getData(@PathVariable long id);
    @PostMapping("/order/{id}")
    FullOrderDTO setOrderStatus(@PathVariable("id") @Min(0) long id, @RequestBody@Valid ChangeStatusDTO dto);
}